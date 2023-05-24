package ssginc_kdt_team3.BE.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ssginc_kdt_team3.BE.DTOs.menu.MenuAddDTO;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ShopMenu {

    @Id
    @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "menu_name", length = 20)
    private String name;

    @NotNull
    @Column(name = "menu_price")
    private int price;

    @NotNull
    @Column(name = "menu_imgurl")
    private String menuImgUrl;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Builder
    public ShopMenu(MenuAddDTO dto, String imgUrl, Shop shop) {
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.menuImgUrl = imgUrl;
        this.shop = shop;
    }
}
