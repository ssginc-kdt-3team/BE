package ssginc_kdt_team3.BE.DTOs.deposit;

import lombok.Data;

@Data
public class CustomerDepositDTO {

    private int origin_value;

    private int pointDiscount;

    private int couponDiscount;

    private int payValue;
}
