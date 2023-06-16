package ssginc_kdt_team3.BE.DTOs.reservation.Alarm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MessageDTO {
  String to;//수신자 번호
  String subject;// LMS인경우 제목
  String content;// 개별 메시지 내용
}
