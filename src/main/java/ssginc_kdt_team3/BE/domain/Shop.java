package ssginc_kdt_team3.BE.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ssginc_kdt_team3.BE.enums.UserStatus;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "shop")
public class Shop {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shpo_id")
    private long id;

    @NotNull
    @Column(name = "shop_name",length = 20)
    private String name;

    @NotNull
    @Column(name = "shop_info", length = 200)
    private String info;

    @NotNull
    @Column(name = "shop_status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    //int -> String으로 수정 (0502 임태경)
    @NotNull
    @Column(name = "shop_location", length = 20)
    private String location;

    @NotNull
    @Column(name = "shop_img", length = 100)
    private String shopImg;

    @NotNull
    @Column(name = "business_img", length = 100)
    private String businessImg;

    @NotNull
    @Column(name = "business_num", length = 100)
    private String businessNum;

    @NotNull
    @Column(name = "business_name", length = 10)
    private String businessName;


    // 변수 명 storeId를 store로 변경해야한다.
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    private Store store;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_operation_info_id")
    private ShopOperationInfo operationInfo;
}
