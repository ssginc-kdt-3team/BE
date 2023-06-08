package ssginc_kdt_team3.BE.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ssginc_kdt_team3.BE.DTOs.reservation.Alarm.MessageDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.Alarm.ResponseSmsDTO;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;
import ssginc_kdt_team3.BE.service.customer.NaverAlarmService;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmScheduler {

    private final JpaDataReservationRepository reservationRepository;

    private final NaverAlarmService naverAlarmService;

    @Scheduled(cron = "0 0,30 * * * *")
    public void almostReservationAlarm() throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {

        LocalDateTime almostTime = LocalDateTime.now().plusMinutes(29);
        LocalDateTime almostTime2 = LocalDateTime.now().plusMinutes(31);
        List<Reservation> AlmostReservation = reservationRepository.findAlmostReservation(almostTime,almostTime2, ReservationStatus.RESERVATION);

        MessageDTO messageDTO = new MessageDTO();

        for (Reservation reservation : AlmostReservation){
            if (reservation.getCustomer().isAlarmBoolean()) {

                String CustomerName = reservation.getCustomer().getName();
                String CustomerPhoneNumber = reservation.getCustomer().getPhoneNumber();

                messageDTO.setTo(CustomerPhoneNumber);
                messageDTO.setContent(CustomerName + "님 입장시간 30분 남았습니다!");

                ResponseSmsDTO responseSmsDTO = naverAlarmService.sendSms(messageDTO);

                log.info("reservation RequestId = {}", responseSmsDTO.getRequestId());
                log.info("reservation RequestId = {}", responseSmsDTO.getRequestId());
                log.info("reservation StatusName = {}", responseSmsDTO.getStatusName());
                log.info("reservation StatusCode = {}", responseSmsDTO.getStatusCode());
            }

        }


    }


}
