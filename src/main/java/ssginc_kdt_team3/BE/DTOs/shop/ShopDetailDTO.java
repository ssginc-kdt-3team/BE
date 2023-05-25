package ssginc_kdt_team3.BE.DTOs.shop;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.domain.ShopMenu;
import ssginc_kdt_team3.BE.enums.ShopStatus;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ShopDetailDTO {
  private Long shopId;
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

  public ShopDetailDTO(Shop shop, List<ShopMenu> shopMenuList) {
    this.shopId = shop.getId();
    this.shopName = shop.getName();
    this.shopInfo = shop.getInfo();
    this.shopStatus = shop.getStatus();
    this.shopLocation = shop.getLocation();
    ShopImg = shop.getShopImgUrl();
    this.shopOpenTime = shop.getOperationInfo().getOpenTime();
    this.shopCloseTime = shop.getOperationInfo().getCloseTime();
    this.menus = new ArrayList<>();

    for (ShopMenu shopMenu: shopMenuList) {
      ShopMenuDto dto = new ShopMenuDto(shopMenu);
      menus.add(dto);
    }
  }
}
