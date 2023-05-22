package ssginc_kdt_team3.BE.DTOs.shop;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import ssginc_kdt_team3.BE.DTOs.customer.ReviewAddRequestDTO;
import ssginc_kdt_team3.BE.DTOs.customer.ReviewResponseDTO;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.domain.ShopMenu;
import ssginc_kdt_team3.BE.enums.ShopStatus;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Slf4j
public class ShopDetailWithReviewResponseDTO {
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

  // 이현: 매장상세페이지 하단 고객후기 추가, 리뷰를 페이지로 넘겨줄거라서
  Page<ReviewResponseDTO> reviewDto;


  public ShopDetailWithReviewResponseDTO(Shop shop, Page<ReviewResponseDTO> reviewDto) { //서비스에서 여기다 값을 담아줄거야
    this.shopId = shop.getId();
    this.shopName = shop.getName();
    this.shopInfo = shop.getInfo();
    this.shopStatus = shop.getStatus();
    this.shopLocation = shop.getLocation();
    ShopImg = shop.getShopImg();
    this.shopOpenTime = shop.getOperationInfo().getOpenTime();
    this.shopCloseTime = shop.getOperationInfo().getCloseTime();
    this.menus = new ArrayList<>();
    this.reviewDto = reviewDto;

    log.info("{}", shop.getShopMenuList().size());

    for (ShopMenu shopMenu: shop.getShopMenuList()) {
      ShopMenuDto dto = new ShopMenuDto(shopMenu);
      menus.add(dto);
    }
  }
}
