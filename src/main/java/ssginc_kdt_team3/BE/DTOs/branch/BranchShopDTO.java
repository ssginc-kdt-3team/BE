package ssginc_kdt_team3.BE.DTOs.branch;

import lombok.*;
import ssginc_kdt_team3.BE.enums.ShopStatus;

@AllArgsConstructor
@Data
public class BranchShopDTO {

    private Long id;
    private String name;
    private String location;
    private String shopImgUrl;
    private ShopStatus shopStatus;
<<<<<<< HEAD
=======

    public BranchShopDTO(long id, String name, String location, String shopImg, ShopStatus shopStatus) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.shopImgUrl = shopImg;
        this.shopStatus = shopStatus;
    }
>>>>>>> c5a8920d31f13e47e0aa34b0b9ff5b539cb47545
}
