package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.reservation.OwnerReservationDTO;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.deposit.DepositRepository;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;
import ssginc_kdt_team3.BE.util.TimeUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OwnerReservationService {

    private final JpaDataReservationRepository reservationRepository;
    private final DepositRepository depositRepository;

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

    public Optional<OwnerReservationDTO> showReservationDetail(Long id) {

        Optional<Reservation> byId = reservationRepository.findById(id);

        if (byId.isPresent()) {
            Reservation reservation = byId.get();
            Deposit reservationDeposit = depositRepository.findReservationDeposit(reservation.getId());

            OwnerReservationDTO dto = new OwnerReservationDTO(reservation,reservationDeposit);

            return Optional.ofNullable(dto);
        }

        return Optional.ofNullable(null);
    }
}
