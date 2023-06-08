package ssginc_kdt_team3.BE.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.reservation.Alarm.MessageDTO;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;
import ssginc_kdt_team3.BE.scheduler.NoShowScheduler;
import ssginc_kdt_team3.BE.service.customer.NaverAlarmService;
import ssginc_kdt_team3.BE.util.TimeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class alarmServiceTest {

    @Autowired
    private NaverAlarmService alarmService;

    @Autowired
    private NoShowScheduler noShow;
    @Autowired
    private JpaDataReservationRepository reservationRepository;

    @Test
    public void noShowAlarmTest() throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {

        LocalDateTime limit = LocalDateTime.now().minusMinutes(30);
        List<Reservation> noShow = reservationRepository.findNoShow(limit, ReservationStatus.RESERVATION);
        MessageDTO message = new MessageDTO();

        for (Reservation reservation : noShow) {
            reservation.setStatus(ReservationStatus.NOSHOW);
            reservation.setChangeTime(TimeUtils.findNow());

            String ownerPhone = reservation.getShop().getOwner().getPhoneNumber();
            String ownerName = reservation.getShop().getOwner().getName();
            LocalDateTime reservationDate = reservation.getReservationDate();
            String content = ownerName + "점주님의 매장에 노쇼가 발생하였습니다!\n예약 일시 : " + reservationDate;
            message.setTo(ownerPhone);
            message.setContent(content);

            alarmService.sendSms(message);

        }

    }
}
