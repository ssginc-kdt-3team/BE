package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Coupon {

    @Id
    @Column(name = "coupon_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_name")
    private String couponName;

    @Column(name = "coupon_type")
    private String type;

    @Column(name = "coupon_discount_value")
    private int discountValue;

    @Column(name = "coupon_discount_rate")
    private double discountRate;

    @Column(name = "coupon_imgurl")
    private String couponImgUrl;

    @Column(name = "coupon_create_date")
    private LocalDateTime createDate;

    @Column(name = "coupon_update_date")
    private LocalDateTime updateDate;
}
