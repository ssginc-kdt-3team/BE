package ssginc_kdt_team3.BE.DTOs.shop;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.domain.ShopMenu;
import ssginc_kdt_team3.BE.enums.ShopCategory;
import ssginc_kdt_team3.BE.enums.ShopStatus;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ShopDetailDTO {
  private Long branchId;
  private Long shopId;
  private String shopName;
  private String shopInfo;
  private ShopStatus shopStatus;
  private String shopLocation;
  private String ShopImg;
  private String phone;

  //ShopOperationInfo
  private LocalTime shopOpenTime;
  private LocalTime shopCloseTime; // 영업 마감시간
  private LocalTime orderCloseTime; // 주문 마감시간

  //ShopMenu테이블
  List<ShopMenuDto> menus;
  //shopCategory
  private ShopCategory shopCategory;

  public ShopDetailDTO(Shop shop, List<ShopMenu> shopMenuList) {
    this.branchId = shop.getBranch().getId();
    this.shopId = shop.getId();
    this.shopName = shop.getName();
    this.shopInfo = shop.getInfo();
    this.shopStatus = shop.getStatus();
    this.shopLocation = shop.getLocation();
    ShopImg = shop.getShopImgUrl();
    this.phone = shop.getPhone();
    this.shopOpenTime = shop.getOperationInfo().getOpenTime();
    this.shopCloseTime = shop.getOperationInfo().getCloseTime();
    this.orderCloseTime = shop.getOperationInfo().getOrderCloseTime();
    this.shopCategory = shop.getCategory();
    this.menus = new ArrayList<>();

    for (ShopMenu shopMenu: shopMenuList) {
      ShopMenuDto dto = new ShopMenuDto(shopMenu);
      menus.add(dto);
    }
  }
}
