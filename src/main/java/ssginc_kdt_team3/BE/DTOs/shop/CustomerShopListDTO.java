package ssginc_kdt_team3.BE.DTOs.shop;

import lombok.Data;
import ssginc_kdt_team3.BE.domain.Shop;

@Data
public class CustomerShopListDTO {
    Long id;
    String name;

    public CustomerShopListDTO(Shop shop) {
        this.id = shop.getId();
        this.name = shop.getName();
    }
}
