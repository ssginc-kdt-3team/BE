package ssginc_kdt_team3.BE.DTOs.reservation.Alarm;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class RequestSmsDTO {
  String type; //SMS,LMS등등
  String contentType;//COMM / AD (AD는 광고메시지 기본값은 COMM)
  String countryCode; //국가 코드 82
  String from; //발신자 번호
  String content; //기본 문자 내용
  List<MessageDTO> messages;
}
