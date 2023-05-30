package ssginc_kdt_team3.BE.DTOs.coupon;

import lombok.Data;


@Data
public class CreateCouponDTO {
  private String couponName;
  private String type; // 정량(value)인지 정률(rate)인지 -> 정량이면 rate = 0
  private int discountValue;
}
