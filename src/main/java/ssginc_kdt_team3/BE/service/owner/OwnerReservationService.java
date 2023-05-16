package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ssginc_kdt_team3.BE.DTOs.reservation.OwnerReservationDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.OwnerReservationDetailDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.OwnerReservationFilterListDTO;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.deposit.DepositRepository;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;
import ssginc_kdt_team3.BE.repository.shop.JpaDataShopRepository;
import ssginc_kdt_team3.BE.service.owner.reservation.OwnerReserveService;
import ssginc_kdt_team3.BE.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OwnerReservationService {

    private final JpaDataReservationRepository reservationRepository;
    private final DepositRepository depositRepository;
    private final JpaDataShopRepository shopRepository;

    public boolean customerCome(Long id) {
        Optional<Reservation> byId = reservationRepository.findById(id);

        if (byId.isPresent()) {
            Reservation reservation = byId.get();

            ReservationStatus status = reservation.getStatus();

            if (status == ReservationStatus.RESERVATION) {
                reservation.setStatus(ReservationStatus.DONE);
                reservation.setChangeTime(TimeUtils.findNow());
                reservationRepository.save(reservation);

                return true;
            }
            return false;
        }
        return false;
    }

    public boolean customerNoShow(Long id) {
        Optional<Reservation> byId = reservationRepository.findById(id);

        if (byId.isPresent()) {
            Reservation reservation = byId.get();

            ReservationStatus status = reservation.getStatus();

            if (status == ReservationStatus.RESERVATION) {
                reservation.setStatus(ReservationStatus.NOSHOW);
                reservation.setChangeTime(TimeUtils.findNow());
                reservationRepository.save(reservation);

                Deposit reservationDeposit = depositRepository.findReservationDeposit(id);
                int originValue = reservationDeposit.getOrigin_value();
                reservationDeposit.setPenaltyValue(originValue);
                reservationDeposit.setStatus(DepositStatus.ALL_PENALTY);

                log.info("reservationDeposit.setStatus========= {}", reservationDeposit.getStatus());
                log.info("reservationDeposit.setPenaltyValue=== {}", reservationDeposit.getPenaltyValue());

                depositRepository.save(reservationDeposit);

                return true;
            }
            return false;
        }
        return false;
    }

    public boolean customerCancel(Long id) {
        Optional<Reservation> byId = reservationRepository.findById(id);

        if (byId.isPresent()) {
            Reservation reservation = byId.get();

            ReservationStatus status = reservation.getStatus();

            if (status == ReservationStatus.RESERVATION) {
                reservation.setStatus(ReservationStatus.CANCEL);
                reservation.setChangeTime(TimeUtils.findNow());
                reservationRepository.save(reservation);

                Deposit reservationDeposit = depositRepository.findReservationDeposit(id);
                reservationDeposit.setStatus(DepositStatus.RETURN);

                log.info("reservationDeposit.setStatus========= {}", reservationDeposit.getStatus());
                log.info("reservationDeposit.setPenaltyValue=== {}", reservationDeposit.getPenaltyValue());

                depositRepository.save(reservationDeposit);

                return true;
            }
            return false;
        }
        return false;
    }

    public Optional<OwnerReservationDetailDTO> showReservationDetail(Long id) {

        Optional<Reservation> byId = reservationRepository.findById(id);

        if (byId.isPresent()) {
            Reservation reservation = byId.get();
            Deposit reservationDeposit = depositRepository.findReservationDeposit(reservation.getId());

            OwnerReservationDetailDTO dto = new OwnerReservationDetailDTO(reservation,reservationDeposit);

            return Optional.ofNullable(dto);
        }

        return Optional.ofNullable(null);
    }

    public List<OwnerReservationFilterListDTO> showShopFilterList(Long ownerId, String filer) {
        List<OwnerReservationFilterListDTO> result = new ArrayList<>();

        Optional<Shop> shopByOwnerId = shopRepository.findShopByOwner_id(ownerId);

        List<Reservation> allByStatusAndShopId = new ArrayList<>();
        if (shopByOwnerId.isPresent()) {
            Shop shop = shopByOwnerId.get();

            if (filer.equals("RESERVATION")) {
                allByStatusAndShopId = reservationRepository.findAllByStatusAndShop_Id(ReservationStatus.RESERVATION, shop.getId());
            } else if (filer.equals("DONE")) {
                allByStatusAndShopId = reservationRepository.findAllByStatusAndShop_Id(ReservationStatus.DONE, shop.getId());
            } else if (filer.equals("CANCEL")) {
                allByStatusAndShopId = reservationRepository.findAllByStatusAndShop_Id(ReservationStatus.CANCEL, shop.getId());
            } else if (filer.equals("IMMINENT")) {
                allByStatusAndShopId = reservationRepository.findAllByStatusAndShop_Id(ReservationStatus.IMMINENT, shop.getId());
            } else if (filer.equals("NOSHOW")) {
                allByStatusAndShopId = reservationRepository.findAllByStatusAndShop_Id(ReservationStatus.NOSHOW, shop.getId());
            } else {
                return result;
            }

            for (Reservation r: allByStatusAndShopId) {
                Deposit reservationDeposit = depositRepository.findReservationDeposit(r.getId());
                OwnerReservationFilterListDTO dto = new OwnerReservationFilterListDTO(r, reservationDeposit);

                result.add(dto);
            }
            return result;
        }

        return result;
    }



}
