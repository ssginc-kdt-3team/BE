package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.deposit.OwnerDepositRepository;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OwnerReservationService {

    private final JpaDataReservationRepository reservationRepository;
    private final OwnerDepositRepository ownerDepositRepository;

    public boolean customerCome(Long id) {
        Optional<Reservation> byId = reservationRepository.findById(id);

        if (byId.isPresent()) {
            Reservation reservation = byId.get();

            ReservationStatus status = reservation.getStatus();

            if (status == ReservationStatus.RESERVATION) {
                reservation.setStatus(ReservationStatus.DONE);
                reservation.setChangeTime(findNow());
                reservationRepository.save(reservation);

                return true;
            }
            return false;
        }
        return false;
    }

    private static LocalDateTime findNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(LocalDateTime.now().toString(), formatter);
    }

    public boolean customerNoShow(Long id) {
        Optional<Reservation> byId = reservationRepository.findById(id);

        if (byId.isPresent()) {
            Reservation reservation = byId.get();

            ReservationStatus status = reservation.getStatus();

            if (status == ReservationStatus.RESERVATION) {
                reservation.setStatus(ReservationStatus.NOSHOW);
                reservation.setChangeTime(findNow());
                reservationRepository.save(reservation);

                Deposit reservationDeposit = ownerDepositRepository.findReservationDeposit(id);
                int originValue = reservationDeposit.getOrigin_value();
                reservationDeposit.setPenaltyValue(originValue);
                reservationDeposit.setStatus(DepositStatus.ALL_PENALTY);

                log.info("reservationDeposit.setStatus========= {}", reservationDeposit.getStatus());
                log.info("reservationDeposit.setPenaltyValue=== {}", reservationDeposit.getPenaltyValue());

                ownerDepositRepository.save(reservationDeposit);

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
                reservation.setChangeTime(findNow());
                reservationRepository.save(reservation);

                Deposit reservationDeposit = ownerDepositRepository.findReservationDeposit(id);
                reservationDeposit.setStatus(DepositStatus.RETURN);

                log.info("reservationDeposit.setStatus========= {}", reservationDeposit.getStatus());
                log.info("reservationDeposit.setPenaltyValue=== {}", reservationDeposit.getPenaltyValue());

                ownerDepositRepository.save(reservationDeposit);

                return true;
            }
            return false;
        }
        return false;
    }
}
