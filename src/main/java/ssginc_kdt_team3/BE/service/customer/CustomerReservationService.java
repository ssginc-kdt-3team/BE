package ssginc_kdt_team3.BE.service.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ssginc_kdt_team3.BE.DTOs.reservation.CustomerReservationAddDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.CustomerReservationListDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.CustomerReservationUpdateDTO;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.customer.JpaCustomerRepository;
import ssginc_kdt_team3.BE.repository.deposit.DepositRepository;
import ssginc_kdt_team3.BE.repository.owner.shop.JpaDateShopRepository;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;
import ssginc_kdt_team3.BE.util.TimeUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomerReservationService {

    private final JpaDataReservationRepository reservationRepository;
    private final JpaDateShopRepository shopRepository;
    private final JpaCustomerRepository customerRepository;
    private final DepositRepository depositRepository;

    @Transactional(readOnly = false)
    public Long makeReservation(CustomerReservationAddDTO dto) {
        Reservation reservation = new Reservation();

        Shop shop = shopRepository.findById(dto.getShopId()).get();
        Customer customer = customerRepository.findCustomer(dto.getUserId()).get();

        //DTO 검증
        setReservationInfo(reservation,dto, shop, customer);
        try {
            Reservation saveReservation = reservationRepository.save(reservation);
            log.info("================================================================can");

            Deposit deposit = new Deposit();

            deposit.setReservation(saveReservation);
            deposit.setStatus(DepositStatus.RECEIVE);
            deposit.setOrigin_value(3000*(dto.getPeople()-dto.getChild()));

            Deposit saveDeposit = depositRepository.save(deposit);

            return saveReservation.getId();

        } catch (Exception e) {
            log.info("================================================================bomb");
            return null;
        }
    }

    public Page<CustomerReservationListDTO> showMyActiveReservation(Pageable pageable, Long id) {
        List<Reservation> allBy = reservationRepository.findAllActive(id, ReservationStatus.RESERVATION);
        Page<CustomerReservationListDTO> customerReservations = listToDTOList(pageable, allBy);

        return customerReservations;
    }

    public Page<CustomerReservationListDTO> showMyAllReservation(Pageable pageable, Long id) {
        List<Reservation> allBy = reservationRepository.findAllMy(id);
        Page<CustomerReservationListDTO> customerReservations = listToDTOList(pageable, allBy);

        return customerReservations;
    }

    public Optional<Reservation> showOneReservation(Long id) {
        return reservationRepository.findById(id);
    }

    public CustomerReservationUpdateDTO showUpdateReservation(Long id) {

        Optional<Reservation> byId = reservationRepository.findById(id);

        if (byId.isPresent()) {
            CustomerReservationUpdateDTO dto = new CustomerReservationUpdateDTO(byId.get());
            return dto;
        }

        return null;
    }


    public boolean updateReservation(Long id, @Validated CustomerReservationUpdateDTO dto) {
        Optional<Reservation> byId = reservationRepository.findById(id);

        if (byId.isPresent()) {
            Reservation reservation = byId.get();

            if (reservation.getStatus() == ReservationStatus.RESERVATION) {
                update(reservation, dto);
                try {
                    reservationRepository.save(reservation);
                    return true;
                } catch (Exception e) {
                    return false;
                }

            }
            return false;
        }
        return false;
    }

    //예약 정보를 dto로 받아온 새로운 정보로 변경
    private void update(Reservation before, CustomerReservationUpdateDTO after) {
        LocalDateTime updateTime = TimeUtils.findNow();
        LocalDateTime reservationTime = TimeUtils.stringParseLocalDataTime(after.getReservationDateTIme());
        String memo = after.getMemo();
        int people = after.getPeople();
        int child = after.getChild();

        before.setPeople(people);
        before.setChild(child);
        before.setMemo(memo);
        before.setReservationDate(reservationTime);
        before.setChangeTime(updateTime);
    }

    private Page<CustomerReservationListDTO> listToDTOList(Pageable pageable, List<Reservation> allBy) {
        List<CustomerReservationListDTO> customerReservationList = new ArrayList<>();

        for ( Reservation r : allBy) {
            CustomerReservationListDTO dto = new CustomerReservationListDTO(r);
            customerReservationList.add(dto);
        }

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), customerReservationList.size());

        Page<CustomerReservationListDTO> customerReservations = new PageImpl<>(customerReservationList.subList(start, end), pageable, customerReservationList.size());
        return customerReservations;
    }

    private void setReservationInfo(Reservation reservation, CustomerReservationAddDTO dto, Shop shop, Customer customer) {

        LocalDateTime reservationTime = TimeUtils.stringParseLocalDataTime(dto.getReservationDate());
        reservation.setReservationDate(reservationTime);
        reservation.setPeople(dto.getPeople());
        reservation.setChild(dto.getChild());
        reservation.setMemo(dto.getMemo());
        reservation.setStatus(ReservationStatus.RESERVATION);

        LocalDateTime parse = TimeUtils.findNow();
        reservation.setApplyTime(parse);
        reservation.setChangeTime(parse);
        reservation.setShop(shop);
        reservation.setCustomer(customer);
    }

    public boolean customerCancel(Long id) {
        Optional<Reservation> byId = reservationRepository.findById(id);

        if (byId.isPresent()) {
            Reservation reservation = byId.get();
            LocalDateTime expectedTime = reservation.getReservationDate();

            if (reservation.getStatus() == ReservationStatus.RESERVATION) {

                if (TimeUtils.findNow().isBefore(expectedTime.minusHours(24))) {
                    //방문 에정일자보다 24시간 이상 여유 있는 경우
                    reservation.setStatus(ReservationStatus.CANCEL);
                    reservation.setChangeTime(TimeUtils.findNow());
                    reservationRepository.save(reservation);

                    //전액 환불 구현하기
                    Deposit reservationDeposit = depositRepository.findReservationDeposit(reservation.getId());
                    reservationDeposit.setStatus(DepositStatus.RETURN);
                    depositRepository.save(reservationDeposit);

                    //쿠폰, 포인트 환불 구현하기

                } else if (TimeUtils.findNow().isBefore(expectedTime.minusHours(8))) {
                    //방문 예정일자보다 8시간 ~ 24시간 여유 있는 경우
                    reservation.setStatus(ReservationStatus.IMMINENT);
                    reservation.setChangeTime(TimeUtils.findNow());
                    reservationRepository.save(reservation);

                    //위약금 50% 환불 50% 구현하기
                    Deposit reservationDeposit = depositRepository.findReservationDeposit(reservation.getId());
                    reservationDeposit.setStatus(DepositStatus.HALF_PENALTY);
                    int originValue = reservationDeposit.getOrigin_value();
                    reservationDeposit.setPenaltyValue(originValue/2);
                    depositRepository.save(reservationDeposit);

                    //쿠폰, 포인트 환불 구현하기

                } else {
                    //방문 예정일자보다 8시간 미만으로 여유있는 경우
                    reservation.setStatus(ReservationStatus.IMMINENT);
                    reservation.setChangeTime(TimeUtils.findNow());
                    reservationRepository.save(reservation);

                    //위약금 100%
                    Deposit reservationDeposit = depositRepository.findReservationDeposit(reservation.getId());
                    reservationDeposit.setStatus(DepositStatus.ALL_PENALTY);
                    int originValue = reservationDeposit.getOrigin_value();
                    reservationDeposit.setPenaltyValue(originValue);
                    depositRepository.save(reservationDeposit);

                    //쿠폰, 포인트 환불 X

                }
                return true;
            }
            return true;
        }
        return false;
    }
}
