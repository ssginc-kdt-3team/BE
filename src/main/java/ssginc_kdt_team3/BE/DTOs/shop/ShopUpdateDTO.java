package ssginc_kdt_team3.BE.DTOs.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopUpdateDTO {
    private String shopName;
    private String shopInfo;
    private UserStatus shopStatus;
    private String shopLocation;
    private String shopImg;
    private String businessName;
    private LocalTime openTime;
    private LocalTime closeTime;
    private LocalTime orderClose;
}
