package ssginc_kdt_team3.BE.DTOs.reservation.Alarm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ResponseSmsDTO {
  String requestId; // 요청ID
  LocalDateTime requestTime; // 요청 시간
  String statusCode; //요청 상태 코드
  String statusName;//요청 상태명
}
