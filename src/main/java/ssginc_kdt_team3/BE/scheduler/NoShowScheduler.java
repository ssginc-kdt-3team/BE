package ssginc_kdt_team3.BE.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.deposit.DepositRepository;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;
import ssginc_kdt_team3.BE.util.TimeUtils;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class NoShowScheduler {

    private final JpaDataReservationRepository reservationRepository;
    private final DepositRepository depositRepository;

    @Scheduled(cron = "0 0,30 * * * *")
    public void updateNoShowReservation() {
        LocalDateTime limit = LocalDateTime.now().minusMinutes(30);
        List<Reservation> noShow = reservationRepository.findNoShow(limit, ReservationStatus.RESERVATION);

        for (Reservation reservation : noShow) {
            reservation.setStatus(ReservationStatus.NOSHOW);
            reservation.setChangeTime(TimeUtils.findNow());

            Deposit reservationDeposit = depositRepository.findReservationDeposit(reservation.getId());

            int originValue = reservationDeposit.getOrigin_value();
            reservationDeposit.setPenaltyValue(originValue);
            reservationDeposit.setStatus(DepositStatus.ALL_PENALTY);

            depositRepository.save(reservationDeposit);
            reservationRepository.save(reservation);
        }

    }

}
