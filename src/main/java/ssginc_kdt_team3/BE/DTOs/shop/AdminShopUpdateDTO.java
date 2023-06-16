package ssginc_kdt_team3.BE.DTOs.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.enums.ShopCategory;
import ssginc_kdt_team3.BE.enums.ShopStatus;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminShopUpdateDTO {
    private String shopName;
    private String shopInfo;
    private ShopStatus shopStatus;
    private String shopLocation;
    private String shopImgUrl;
    private String businessName;
    private LocalTime openTime;
    private LocalTime closeTime;
    private LocalTime orderClose;
}
