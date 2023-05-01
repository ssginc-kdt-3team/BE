package ssginc_kdt_team3.BE.DTOs.owner;

import com.sun.istack.NotNull;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "shop")
public class Shop {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String status;

    @NotNull
    @Column(name = "shop_location", length = 20)
    private int location;

    @NotNull
    @Column(name = "shop_img", length = 100)
    private String img;

    @NotNull
    @Column(name = "business_img", length = 100)
    private String business_img;

    @NotNull
    @Column(name = "business_num", length = 100)
    private String business_num;

    @NotNull
    @Column(name = "business_name", length = 10)
    private String business_name;

    @NotNull
    @OneToMany
    @JoinColumn(name = "store_id")
    private long store_id;


}
