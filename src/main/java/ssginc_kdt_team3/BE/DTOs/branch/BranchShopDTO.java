package ssginc_kdt_team3.BE.DTOs.branch;

import lombok.*;
import ssginc_kdt_team3.BE.enums.ShopStatus;

@NoArgsConstructor
@Data
@Getter
@Setter
public class BranchShopDTO {
    private long id;
    private String name;
    private String location;
    private String shopImg;
    private ShopStatus shopStatus;

    public BranchShopDTO(long id, String name, String location, String shopImg, ShopStatus shopStatus) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.shopImg = shopImg;
        this.shopStatus = shopStatus;
    }
}
