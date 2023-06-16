package ssginc_kdt_team3.BE.DTOs.coupon;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.CouponProvide;
import ssginc_kdt_team3.BE.enums.CouponStatus;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CustomerCouponListDTO {
  private Long couponId;
  private String couponCode;
  private String couponName;
  private LocalDate givenDay;
  private LocalDate outDay; //유효기간
  private CouponStatus status;
  private String reason;
  private int discountValue;

  // 모델매퍼로 바꿔보자
  public CustomerCouponListDTO(CouponProvide couponProvide) {
    this.couponId = couponProvide.getId();
    this.couponCode = couponProvide.getCouponCode();
    this.couponName = couponProvide.getCoupon().getCouponName();
    this.givenDay = couponProvide.getGivenDay();
    this.outDay = couponProvide.getOutDay();
    this.status = couponProvide.getStatus();
    this.reason = couponProvide.getReason();
    this.discountValue = couponProvide.getCoupon().getDiscountValue();
  }
}
