package ssginc_kdt_team3.BE.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "shop_menu")
public class Shop_menu {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "menu_name",length = 20)
    private String name;

    @NotNull
    @Column(name = "menu_price")
    private int price;



}
