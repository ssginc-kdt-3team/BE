package ssginc_kdt_team3.BE.DTOs.shop;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.DTOs.menu.MenuDTO;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.enums.ShopStatus;

import java.util.List;

@Data
@NoArgsConstructor
public class AdminShopDetailDTO extends OwnerShopDetailDTO {

    private String businessImgUrl;
    private ShopStatus shopStatus;

    public AdminShopDetailDTO(Shop shop, List<MenuDTO> menus) {
        this.ownerId = shop.getOwner().getId();
        this.shopId = shop.getId();
        this.businessImgUrl = shop.getShopImgUrl();
        this.businessCeo = shop.getBusinessName();
        this.businessNumber = shop.getBusinessNum();
        this.shopImgUrl = shop.getShopImgUrl();
        this.shopName = shop.getName();
        this.location = shop.getLocation();
        this.shopInfo = shop.getInfo();
        this.shopStatus = shop.getStatus();
        this.branchName = shop.getBranch().getName();
        this.ownerName = shop.getOwner().getName();

        this.openTime = shop.getOperationInfo().getOpenTime().toString();
        this.orderCloseTime = shop.getOperationInfo().getOrderCloseTime().toString();
        this.closeTime = shop.getOperationInfo().getCloseTime().toString();
        this.seat = shop.getOperationInfo().getSeats();
        this.menus = menus;
    }

}
