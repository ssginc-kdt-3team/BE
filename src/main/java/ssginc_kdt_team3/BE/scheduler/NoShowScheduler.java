package ssginc_kdt_team3.BE.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ssginc_kdt_team3.BE.DTOs.reservation.Alarm.MessageDTO;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.deposit.DepositRepository;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;
import ssginc_kdt_team3.BE.service.customer.NaverAlarmService;
import ssginc_kdt_team3.BE.util.TimeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class NoShowScheduler {

    private final JpaDataReservationRepository reservationRepository;
    private final DepositRepository depositRepository;
    private final NaverAlarmService alarmService;

    @Scheduled(cron = "0 0,30 * * * *")
    public void updateNoShowReservation() throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        LocalDateTime limit = LocalDateTime.now().minusMinutes(30);
        List<Reservation> noShow = reservationRepository.findNoShow(limit, ReservationStatus.RESERVATION);
        MessageDTO message = new MessageDTO();

        for (Reservation reservation : noShow) {
            reservation.setStatus(ReservationStatus.NOSHOW);
            reservation.setChangeTime(TimeUtils.findNow());

            String ownerPhone = reservation.getShop().getOwner().getPhoneNumber();
            String ownerName = reservation.getShop().getOwner().getName();
            LocalDateTime reservationDate = reservation.getReservationDate();
            String content = ownerName + " 점주님의 매장에 노쇼가 발생하였습니다!\n예약 ID : " + reservation.getId() + "\n예약 일시 : " + reservationDate +
                    "\n노쇼 발생시간 : " + reservation.getChangeTime();
            message.setTo(ownerPhone);
            message.setContent(content);
            //신영 추가 0608 -> 노쇼시 점주에게 문자알림

            Deposit reservationDeposit = depositRepository.findReservationDeposit(reservation.getId());

            int originValue = reservationDeposit.getOrigin_value();
            reservationDeposit.setPenaltyValue(originValue);
            reservationDeposit.setStatus(DepositStatus.ALL_PENALTY);

            depositRepository.save(reservationDeposit);
            reservationRepository.save(reservation);

            alarmService.sendSms(message);
        }

    }

}
