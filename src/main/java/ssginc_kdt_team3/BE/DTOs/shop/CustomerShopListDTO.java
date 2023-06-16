package ssginc_kdt_team3.BE.DTOs.shop;

import lombok.Data;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.enums.ShopCategory;

@Data
public class CustomerShopListDTO {
    Long id;
    String name;
    private ShopCategory shopCategory;


    public CustomerShopListDTO(Shop shop) {
        this.id = shop.getId();
        this.name = shop.getName();
        this.shopCategory = shop.getCategory();

    }
}
