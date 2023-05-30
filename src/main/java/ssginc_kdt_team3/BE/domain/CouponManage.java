package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import lombok.Setter;
import ssginc_kdt_team3.BE.enums.CouponManageType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class CouponManage {

    @Id
    @Column(name = "coupon_manage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_manage_type")
    @Enumerated(EnumType.STRING)
    private CouponManageType manageType;

    @Column(name = "coupon_manage_create_date")
    private LocalDate createDate;

    @Column(name = "coupon_manage_update_date")
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
}
