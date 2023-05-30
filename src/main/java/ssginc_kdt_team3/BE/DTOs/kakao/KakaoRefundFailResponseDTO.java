package ssginc_kdt_team3.BE.DTOs.kakao;

import lombok.*;

@Data
@NoArgsConstructor
public class KakaoRefundFailResponseDTO extends KakaoRefundResponseDTO{
    private String failReason; // 요청 고유 번호
}
