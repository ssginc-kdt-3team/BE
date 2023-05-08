package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import lombok.ToString;
import ssginc_kdt_team3.BE.DTOs.cust.Address;
import ssginc_kdt_team3.BE.enums.StoreStatus;

import javax.persistence.*;

@Entity
@Getter
@ToString
public class Store {

    @Id
    @Column(name = "store_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "str_name", length = 20)
    private String name;

    //String -> Address 수정 (0502 임태경)
    //Column명 각각 지정하는 방식으로 변경 (0502 임태경)
    @AttributeOverride(name = "city", column = @Column(name = "str_city"))
    @AttributeOverride(name = "district", column = @Column(name = "str_district"))
    @AttributeOverride(name = "detail", column = @Column(name = "str_detail"))
    @AttributeOverride(name = "zipCode", column = @Column(name = "str_zipCode"))
    private Address address;

    @Column(name = "str_phone", length = 20)
    private String phone;

    //Enumerated EnumType 추가 (0503 임태경)
    @Column(name = "str_status")
    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "st_op_id")
    private StoreOperationInfo storeOperationInfoId;

}
