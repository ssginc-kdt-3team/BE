package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import ssginc_kdt_team3.BE.DTOs.cust.Address;
import ssginc_kdt_team3.BE.enums.BranchStatus;

import javax.persistence.*;

@Entity
@Getter
public class Branch {

    @Id
    @Column(name = "branch_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "branch_name", length = 20)
    private String name;

    //String -> Address 수정 (0502 임태경)
    //Column명 각각 지정하는 방식으로 변경 (0502 임태경)
    @AttributeOverride(name = "city", column = @Column(name = "branch_city"))
    @AttributeOverride(name = "district", column = @Column(name = "branch_district"))
    @AttributeOverride(name = "detail", column = @Column(name = "branch_detail"))
    @AttributeOverride(name = "zipCode", column = @Column(name = "branch_zipCode"))
    private Address address;

    @Column(name = "branch_phone", length = 20)
    private String phone;

    //Enumerated EnumType 추가 (0503 임태경)
    @Column(name = "branch_status")
    @Enumerated(EnumType.STRING)
    private BranchStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_operation_id")
    private BranchOperationInfo branchOperationInfoId;

}
