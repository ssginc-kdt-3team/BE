package ssginc_kdt_team3.BE.DTOs.shop;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.DTOs.menu.MenuDTO;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.enums.ShopCategory;

import java.util.List;

@Data
@NoArgsConstructor
public class OwnerShopDetailDTO {

    Long ownerId; // 점주 id
    Long shopId; // 가게 id

    String businessCeo; // 사업장 등록주
    String businessNumber; // 사업장 등록번호

    String shopImgUrl; // 매장 이미지
    String shopName; // 매장 이름
    String location; //지점 내 위치
    String shopInfo; // 매장 설명

    String branchName; // 브랜치 name
    String ownerName; // 점장 이름

    String openTime;  // 오픈 시간
    String orderCloseTime; // 마감 시간
    String closeTime; // 문닫는 시간

    int seat; // 매장 최대 예약 가능 수

    List<MenuDTO> menus;

    ShopCategory shopCategory;

    public OwnerShopDetailDTO(Shop shop, List<MenuDTO> menus) {
        this.ownerId = shop.getOwner().getId();
        this.shopId = shop.getId();
        this.businessCeo = shop.getBusinessName();
        this.businessNumber = shop.getBusinessNum();
        this.shopImgUrl = shop.getShopImgUrl();
        this.shopName = shop.getName();
        this.location = shop.getLocation();
        this.shopInfo = shop.getInfo();
        this.branchName = shop.getBranch().getName();
        this.ownerName = shop.getOwner().getName();

        this.shopCategory = shop.getCategory();

        this.openTime = shop.getOperationInfo().getOpenTime().toString();
        this.orderCloseTime = shop.getOperationInfo().getOrderCloseTime().toString();
        this.closeTime = shop.getOperationInfo().getCloseTime().toString();
        this.seat = shop.getOperationInfo().getSeats();
        this.menus = menus;
    }
}
