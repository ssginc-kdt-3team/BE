package ssginc_kdt_team3.BE.domain;

import com.sun.istack.NotNull;
import lombok.*;
import ssginc_kdt_team3.BE.DTOs.shop.OwnerShopUpdateDTO;
import ssginc_kdt_team3.BE.enums.ShopStatus;
import ssginc_kdt_team3.BE.util.TimeUtils;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    @NotNull
    @Column(name = "shop_name",length = 20)
    private String name;

    @NotNull
    @Column(name = "shop_info", length = 200)
    private String info;

    @NotNull
    @Column(name = "shop_status")
    @Enumerated(EnumType.STRING)
    private ShopStatus status;

    //int -> String으로 수정 (0502 임태경)
    @NotNull
    @Column(name = "shop_location", length = 20)
    private String location;

    @Column(name = "shop_imgurl")
    private String shopImgUrl;

    @Column(name = "business_img")
    private String businessImg;

    @Column(name = "business_num", length = 100)
    private String businessNum;

    @Column(name = "business_ceo", length = 10)
    private String businessName;

    @Column(name = "shop_phone")
    private String phone;

    // 변수 명 storeId를 store로 변경해야한다.
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id")
    private Owner owner;

    @NotNull
    @OneToOne
    @JoinColumn(name = "shop_operation_id")
    private ShopOperationInfo operationInfo;

    @OneToMany(mappedBy = "shop")
    private List<ShopMenu> shopMenuList = new ArrayList<>();

    @OneToMany(mappedBy = "shop")
    private List<Reservation> reservationList = new ArrayList<>();

    @Builder
    public Shop(long id, String name, String info, ShopStatus status, String location, String shopImg, String businessImg, String businessNum, String businessName, Branch branch, Owner owner, ShopOperationInfo operationInfo, String phone) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.status = status;
        this.location = location;
        this.shopImgUrl = shopImg;
        this.businessImg = businessImg;
        this.businessNum = businessNum;
        this.businessName = businessName;
        this.branch = branch;
        this.owner = owner;
        this.operationInfo = operationInfo;
        this.phone = phone;
    }

    public boolean update(Long shopId, OwnerShopUpdateDTO updateDTO) {

        if (shopId.equals(this.id)) {
            this.name = updateDTO.getShopName(); // 매장 이름         v
            this.info = updateDTO.getShopInfo(); // 매장 설명         v

            LocalTime openTime = TimeUtils.stringParseLocalTime(updateDTO.getOpenTime());
            LocalTime closeTime = TimeUtils.stringParseLocalTime(updateDTO.getCloseTime());
            LocalTime orderCloseTime = TimeUtils.stringParseLocalTime(updateDTO.getOrderCloseTime());

            this.operationInfo.update(openTime, orderCloseTime, closeTime, updateDTO.getSeat() );  // 오픈 시간        v

            this.shopImgUrl = updateDTO.getShopImgUrl();

            return true;
        }
        return false;
    }
}
