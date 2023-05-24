package ssginc_kdt_team3.BE.DTOs.shop;

import lombok.Data;

@Data
public class OwnerShopUpdateDTO {

    private String shopName; // 매장 이름         v
    private String shopInfo; // 매장 설명         v

    private String openTime;  // 오픈 시간        v
    private String orderCloseTime; // 마감 시간   v
    private String closeTime; // 문닫는 시간      v
    private int seat; // 매장 최대 예약 가능 수   v

    private String shopImgUrl;
}
