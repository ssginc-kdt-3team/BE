package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import ssginc_kdt_team3.BE.enums.StoreStatus;

import javax.persistence.*;

@Entity
@Getter
public class Store {

    @Id
    @Column(name = "store_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "str_name", length = 20)
    private String name;

    @Column(name = "str_address", length = 100)
    private String address;

    @Column(name = "str_phone", length = 20)
    private String phone;

    @Column(name = "str_status")
    private StoreStatus status;

    @OneToOne
    @JoinColumn(name = "st_op_id")
    private StoreOperationInfo storeOperationInfoId;




}
