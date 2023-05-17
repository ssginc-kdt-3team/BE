package ssginc_kdt_team3.BE.DTOs.shop;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.domain.ShopMenu;
import ssginc_kdt_team3.BE.enums.ShopStatus;
import javax.persistence.Entity;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
@Data
public class ShopDetailDTO {
    //Shop테이블
    private long shopId;
    private String shopName;
    private String shopInfo;
    private ShopStatus shopStatus;
    private String shopLocation;
    private String ShopImg;

    //ShopOperationInfo
    private LocalTime shopOpenTime;
    private LocalTime shopCloseTime;

    //ShopMenu테이블
    List<ShopMenuDto> menus;

    public ShopDetailDTO(Shop shop) {
        this.shopId = shop.getId();
        this.shopName = shop.getName();
        this.shopInfo = shop.getInfo();
        this.shopStatus = shop.getStatus();
        this.shopLocation = shop.getLocation();
        ShopImg = shop.getShopImg();
        this.shopOpenTime = shop.getOperationInfo().getOpenTime();
        this.shopCloseTime = shop.getOperationInfo().getCloseTime();
        this.menus = new ArrayList<>();

        log.info("{}", shop.getShopMenuList().size());

        for (ShopMenu shopMenu: shop.getShopMenuList()) {
            ShopMenuDto dto = new ShopMenuDto(shopMenu);
            menus.add(dto);
        }
    }

    //Review테이블
//    private long reviewId;
//    private String reviewTitle;
//    private String reviewContents;
//    private LocalDateTime reviewTime;

}
