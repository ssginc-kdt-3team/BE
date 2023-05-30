package ssginc_kdt_team3.BE.service.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ssginc_kdt_team3.BE.DTOs.reservation.*;
import ssginc_kdt_team3.BE.DTOs.reservation.Alarm.MessageDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.Alarm.ResponseSmsDTO;
import ssginc_kdt_team3.BE.domain.*;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.customer.JpaCustomerRepository;
import ssginc_kdt_team3.BE.repository.deposit.DepositRepository;
import ssginc_kdt_team3.BE.repository.review.JpaDataReviewRepository;
import ssginc_kdt_team3.BE.repository.shop.JpaDataShopRepository;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;
import ssginc_kdt_team3.BE.util.TimeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomerReservationService {

    private final JpaDataReservationRepository reservationRepository;
    private final JpaDataShopRepository shopRepository;
    private final JpaCustomerRepository customerRepository;
    private final DepositRepository depositRepository;
    private final JpaDataReviewRepository reviewRepository;
    private final NaverAlarmService naverAlarmService;


    @Transactional(readOnly = false)
    public Long makeReservation(CustomerReservationAddDTO dto) {
        Reservation reservation = new Reservation();

        Shop shop = shopRepository.findById(dto.getShopId()).get();
        Customer customer = customerRepository.findCustomer(dto.getUserId()).get();

        //DTO 검증
        setReservationInfo(reservation, dto, shop, customer);
        try {
            Reservation saveReservation = reservationRepository.save(reservation);
            log.info("================================================================can");

            Deposit deposit = new Deposit();

            deposit.setReservation(saveReservation);
            deposit.setStatus(DepositStatus.RECEIVE);
            deposit.setOrigin_value(3000 * (dto.getPeople() - dto.getChild()));

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

        if (dto.getPeople() <= dto.getChild()) {
            return false;
        }


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
        LocalDateTime reservationTime = TimeUtils.stringParseLocalDataTime(after.getReservationDateTime());
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

        for (Reservation r : allBy) {
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

    public boolean customerCancel(Long id) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
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

                    String CustomerName = reservation.getCustomer().getName();
                    String CustomerPhone = reservation.getCustomer().getPhoneNumber();
                    String CustomerContent = CustomerName + "님의 예약 취소가 완료되었습니다.";

                    MessageDTO messageDTO = new MessageDTO();
                    messageDTO.setTo(CustomerPhone);
                    messageDTO.setContent(CustomerContent);

                    ResponseSmsDTO response = naverAlarmService.sendSms(messageDTO);

                    System.out.println("response : " + response);
                    System.out.println(response.getRequestId());
                    System.out.println(response.getStatusCode());
                    System.out.println(response.getRequestTime());
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
                    reservationDeposit.setPenaltyValue(originValue / 2);
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

    public Optional<CustomerReservationDetailDTO> findOne(Long id) {
        Optional<Reservation> byId = reservationRepository.findById(id);

        if (byId.isPresent()) {
            Reservation reservation = byId.get();
            Deposit reservationDeposit = depositRepository.findReservationDeposit(reservation.getId());

            Optional<Review> byReservationId = reviewRepository.findByReservationId(reservation.getId());

            boolean canReview = false;

            log.info("예약 가능 일자 {}",LocalDateTime.now().isBefore(reservation.getReservationDate().plusDays(7)) );
            log.info("예약 여부 {}", reservation.getStatus().equals(ReservationStatus.DONE));
            if (!byReservationId.isPresent() && reservation.getStatus().equals(ReservationStatus.DONE) && LocalDateTime.now().isBefore(reservation.getReservationDate().plusDays(7))) {
                log.info("예약 가능 일자 {}",LocalDateTime.now().isBefore(reservation.getReservationDate().plusDays(7)) );
                canReview = true;
            }

            //리뷰가 존재하면 작성 불가 false, 존재하지 않으면 작성가능 ture
            CustomerReservationDetailDTO dto = new CustomerReservationDetailDTO(reservation, reservationDeposit, canReview);

            return Optional.ofNullable(dto);
        }

        return Optional.ofNullable(null);
    }

    public List<reservationPossibleDTO> canReservation(Long shopId, String date) {
        Optional<Shop> byId = shopRepository.findById(shopId);
        List<reservationPossibleDTO> result = new ArrayList<>();

        LocalDateTime today = TimeUtils.findNow();
        LocalDate getDate = LocalDate.parse(date);


        LocalDate dayLimit = null;
        if (today.getDayOfMonth() <= 15) {
            dayLimit = LocalDate.of(today.getYear(), today.getMonth().plus(1), 15);

        } else {
            dayLimit = LocalDate.of(today.getYear(), today.getMonth().plus(1), today.getMonth().plus(1).maxLength());
        }

        if (getDate.isAfter(dayLimit) || getDate.isBefore(today.toLocalDate())) {
            return result;
        }


        if (byId.isPresent()) {
            Shop shop = byId.get();
            int limit = shop.getOperationInfo().getSeats();
            LocalTime openTime = shop.getOperationInfo().getOpenTime();
            LocalTime orderCloseTime = shop.getOperationInfo().getOrderCloseTime();

            long temp = 1L;
            for (LocalTime time = openTime; time.isBefore(orderCloseTime); time = time.plusMinutes(30)) {

                LocalDateTime when = LocalDateTime.of(getDate, time);

                int cnt = reservationRepository.countByReservationDateAndShop_Id(when, shopId);

                if (cnt < limit) {
                    result.add(new reservationPossibleDTO(temp, time, true));
                } else {
                    result.add(new reservationPossibleDTO(temp, time, false));
                }

                temp += 1;
            }
            return result;
        }

        return result;
    }


}
