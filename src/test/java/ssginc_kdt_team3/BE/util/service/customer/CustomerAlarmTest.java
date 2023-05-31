package ssginc_kdt_team3.BE.util.service.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

@SpringBootTest
public class CustomerAlarmTest {

    @Autowired
    NaverAlarmService naverAlarmService;
    @Autowired
    JpaDataReservationRepository reservationRepository;

    @Test
    public void almostReservationAlarm() throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {

        LocalDateTime almostTime = LocalDateTime.now().plusMinutes(29);
        LocalDateTime almostTime2 = LocalDateTime.now().plusMinutes(31);
        List<Reservation> AlmostReservation = reservationRepository.findAlmostReservation(almostTime,almostTime2, ReservationStatus.RESERVATION);

        MessageDTO messageDTO = new MessageDTO();

        for (Reservation reservation : AlmostReservation) {
            String CustomerName = reservation.getCustomer().getName();
            String CustomerPhoneNumber = reservation.getCustomer().getPhoneNumber();

            messageDTO.setTo(CustomerPhoneNumber);
            messageDTO.setContent(CustomerName + "님 입장시간 30분 남았습니다!");

            ResponseSmsDTO responseSmsDTO = naverAlarmService.sendSms(messageDTO);

            System.out.println("reponseSmsDTO = "+ responseSmsDTO.getRequestId());
            System.out.println("reponseSmsDTO = "+ responseSmsDTO.getRequestTime());
            System.out.println("reponseSmsDTO = "+ responseSmsDTO.getStatusCode());
            System.out.println("reponseSmsDTO = "+ responseSmsDTO.getStatusName());

        }

    }
}
