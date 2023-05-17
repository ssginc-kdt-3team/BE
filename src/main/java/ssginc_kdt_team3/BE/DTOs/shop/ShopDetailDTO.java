package ssginc_kdt_team3.BE.DTOs.shop;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ssginc_kdt_team3.BE.enums.ShopStatus;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@Data
@Entity
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
    private long menuId;
    private String menuName;
    private int menuPrice;
    private String menuImg;

    //Review테이블
//    private long reviewId;
//    private String reviewTitle;
//    private String reviewContents;
//    private LocalDateTime reviewTime;

}
