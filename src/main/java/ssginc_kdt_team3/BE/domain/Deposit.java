package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import lombok.Setter;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.enums.ReservationStatus;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Deposit {

    @Id
    @Column(name = "deposit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deposit_status")
    @Enumerated(EnumType.STRING)
    private DepositStatus status;

    @Column(name = "deposit_origin_value")
    private int origin_value;

    @Column(name = "deposit_point_discount")
    private int pointDiscount;

    @Column(name = "deposit_coupon_discount")
    private int couponDiscount;

    @Column(name = "deposit_pay_value")
    private int pay_value;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @OneToOne
    @JoinColumn(name = "coupon_provide_id")
    private CouponProvide coupon;

}
