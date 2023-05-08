package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import lombok.Setter;
import ssginc_kdt_team3.BE.enums.CouponStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class CouponProvide {

    @Id
    @Column(name = "coupon_provide_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_provide_code")
    private String couponCode;

    @Column(name = "coupon_provide_givenday")
    private LocalDate givenDay;

    @Column(name = "coupon_provide_outDay")
    private LocalDate outDay;

    @Column(name = "coupon_provid_status")
    private CouponStatus status;

    @Column(name = "coupon_provid_changing_reason")
    private String reason;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "deposit_id")
    private Deposit deposit;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
}
