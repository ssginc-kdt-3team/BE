package ssginc_kdt_team3.BE.DTOs.menu;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.ShopMenu;

@Data
@NoArgsConstructor
public class MenuUpdateDTO {

    private String name;

    private int price;

    private String menuImgUrl;

    public MenuUpdateDTO(ShopMenu shopMenu) {
        this.name = shopMenu.getName();
        this.price = shopMenu.getPrice();
        this.menuImgUrl = shopMenu.getMenuImgUrl();
    }
}
