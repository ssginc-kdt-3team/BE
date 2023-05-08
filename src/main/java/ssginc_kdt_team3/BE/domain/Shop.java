package ssginc_kdt_team3.BE.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import ssginc_kdt_team3.BE.enums.ShopStatus;

import javax.persistence.*;


@Entity
@Getter
@Setter
public class Shop {
    @Id
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
    private ShopStatus status;

    //int -> String으로 수정 (0502 임태경)
    @NotNull
    @Column(name = "shop_location", length = 20)
    private String location;

    @Column(name = "shop_imgurl")
    private String shopImg;

    @Column(name = "business_img")
    private String businessImg;

    @Column(name = "business_num", length = 100)
    private String businessNum;

    @Column(name = "business_ceo", length = 10)
    private String businessName;

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
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_operation_id")
    private ShopOperationInfo operationInfo;
}
