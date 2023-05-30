package ssginc_kdt_team3.BE.domain;

import lombok.*;
import ssginc_kdt_team3.BE.DTOs.kakao.KakaoPayApproveResponseDTO;
import ssginc_kdt_team3.BE.util.TimeUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
public class PaymentManaging {

    @Id
    @Column(name = "payment_managing_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_managing_tid")
    private String tid;

    @Column(name = "payment_managing_amount")
    private int amount;

    @Column(name = "payment_managing_tax_free")
    private int texFree;

    @Column(name = "payment_managing_vat")
    private int vat;

    @Column(name = "payment_managing_date")
    private LocalDateTime date;

    public PaymentManaging(KakaoPayApproveResponseDTO payApproveResponseDTO) {
        this.tid = payApproveResponseDTO.getTid();
        this.amount = payApproveResponseDTO.getAmount().getTotal();
        this.texFree = payApproveResponseDTO.getAmount().getTax_free();
        this.vat = payApproveResponseDTO.getAmount().getTax();
        this.date = TimeUtils.stringParseLocalDataTime(payApproveResponseDTO.getApproved_at().replace("T", " "));
    }
}
