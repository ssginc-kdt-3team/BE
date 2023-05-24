package ssginc_kdt_team3.BE.DTOs.branch;

import lombok.*;
import ssginc_kdt_team3.BE.enums.ShopStatus;

@AllArgsConstructor
@Data
public class BranchShopDTO {

    private Long id;
    private String name;
    private String location;
    private String shopImg;
    private ShopStatus shopStatus;
}
