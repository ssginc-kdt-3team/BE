package ssginc_kdt_team3.BE.DTOs.menu;

import lombok.Data;
import ssginc_kdt_team3.BE.domain.ShopMenu;

@Data
public class MenuDTO {
    private long id;

    private String name;

    private int price;

    private String menuImgUrl;

    public MenuDTO(ShopMenu shopMenu) {
        this.id = shopMenu.getId();
        this.name = shopMenu.getName();
        this.price = shopMenu.getPrice();
        this.menuImgUrl = shopMenu.getMenuImgUrl();
    }
}
