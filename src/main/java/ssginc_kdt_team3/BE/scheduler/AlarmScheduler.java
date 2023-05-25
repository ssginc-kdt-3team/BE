package ssginc_kdt_team3.BE.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ssginc_kdt_team3.BE.DTOs.reservation.ReservationAlarmDTO;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.service.customer.NaverAlarmService;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AlarmScheduler {

    @Autowired
    private final NaverAlarmService naverAlarmService;

    private final String API_URL = "https://sens.apigw.ntruss.com/sms/v2";
    private final String API_KEY = "ncp:sms:kr:305677338657:project_yes_team";
    //네이버 SMS 서비스 API URL&Key

    private final LocalDateTime nowTime = LocalDateTime.now();
    private final LocalDateTime reservationAlarmTime = nowTime.plusMinutes(30);
    //"예약 상태"일시 현재시간 - 30분 시간부터 10분 간격으로 메시지 발신

    @Scheduled(cron = "0 */1 * * *")//1분마다 상태변화 감지
    public void ReservationStatus() {

        OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),JsonMessageCreate());
            Request request = new Request.Builder()
                    .url(API_URL + "services/{serviceId}/messages")
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .addHeader("x-ncp-apigw-signature-v2", API_KEY)
                    .post(body)
                    .build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                assert response.body() != null;//null일경우 예외 발생
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    private String JsonMessageCreate(){

        List<ReservationAlarmDTO> toAlarm = naverAlarmService.reservationAlarmDTOList();

        HashMap<String,Object> requestMap = new HashMap<>();
        HashMap<String,String> requestMessage = new HashMap<>();

        requestMap.put("type","SMS");//타입
        requestMap.put("from","01032028829");//발신자 번호


        LocalDateTime reservationTime;//실제 예약 시간
        Duration duration;//남은 시간 (예약시간 - (현재시간-30분))을 담을 변수
        long remainingTime;//남은시간 메시지에 추가하기위해 사용

        for (ReservationAlarmDTO DTO: toAlarm) {

            reservationTime = DTO.getReservationDate();
            duration = Duration.between(reservationTime, reservationAlarmTime);
            remainingTime = duration.toMinutes();


            if((DTO.getReservationStatus().equals(ReservationStatus.RESERVATION) && 0 < remainingTime) &&(
            remainingTime == 10 || remainingTime == 20 || remainingTime == 30)){
                //예약 상태 + 예약 시간 30분 전부터 SMS발신
                requestMessage.put("subject", "예약 알림");//제목
                requestMessage.put("to", DTO.getPhoneNumber());//수신자 번호
                requestMessage.put("content", "입장 시간 " + remainingTime + "분 전 입니다!");//내용
                requestMap.put("message", requestMessage);

                return requestMap.toString();
            }
            else if (DTO.getReservationStatus().equals(ReservationStatus.RESERVATION)
                    && remainingTime == 0) {
                requestMessage.put("subject", "입장 알림");//제목
                requestMessage.put("to", DTO.getPhoneNumber());//수신자 번호
                requestMessage.put("content", "입장 시간입니다! 입장해주세요!");//내용
                requestMap.put("message", requestMessage);

                return requestMap.toString();

            } else if (DTO.getReservationStatus().equals(ReservationStatus.DONE)) {
                requestMessage.put("subject", "입장 완료 알림");//제목
                requestMessage.put("to", DTO.getPhoneNumber());//수신자 번호
                requestMessage.put("content", "입장 확인되었습니다!");//내용
                requestMap.put("message", requestMessage);

                return requestMap.toString();

            } else if (DTO.getReservationStatus().equals(ReservationStatus.CANCEL)) {
                requestMessage.put("subject", "예약 취소 알림");//제목
                requestMessage.put("to", DTO.getPhoneNumber());//수신자 번호
                requestMessage.put("content", "예약이 취소되었습니다.");//내용
                requestMap.put("message", requestMessage);

                return requestMap.toString();
            } else if (DTO.getReservationStatus().equals(ReservationStatus.IMMINENT)) {
                requestMessage.put("subject", "예약 취소 및 위약금 발생안내");//제목
                requestMessage.put("to", DTO.getPhoneNumber());//수신자 번호
                requestMessage.put("content", "예약취소로 인하여 일부 위약금이 발생됩니다.");//내용
                requestMap.put("message", requestMessage);

                return requestMap.toString();
            } else if(DTO.getReservationStatus().equals(ReservationStatus.NOSHOW)){
                requestMessage.put("subject", "노쇼 안내");//제목
                requestMessage.put("to", DTO.getPhoneNumber());//수신자 번호
                requestMessage.put("content", "노쇼가 발생하였습니다.패널티가 부가됩니다.");//내용
                requestMap.put("message", requestMessage);

                return requestMap.toString();
                }
            }
            return "조건문에 맞는게 없음,에러 발생";
        }
    }


