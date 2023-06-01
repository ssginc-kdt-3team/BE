package ssginc_kdt_team3.BE.DTOs.reservation.pay;

import lombok.Data;

@Data
public class CustomerDiscountDTO {

    private Long couponId;
    private int couponDiscount;
    private int pointDiscount;
}
