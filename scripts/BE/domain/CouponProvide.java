package ssginc_kdt_team3.BE.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CouponProvide {

    @Id
    @Column(name = "coupon_provide_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponProvideId;
}
