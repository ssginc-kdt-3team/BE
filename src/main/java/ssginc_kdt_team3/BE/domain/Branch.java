package ssginc_kdt_team3.BE.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.DTOs.branch.BranchUpdateDTO;
import ssginc_kdt_team3.BE.DTOs.Address;
import ssginc_kdt_team3.BE.enums.BranchStatus;
import ssginc_kdt_team3.BE.util.TimeUtils;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
public class Branch {

    @Id
    @Column(name = "branch_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "branch_name", length = 20)
    private String name;

    @Column(name = "branch_img_url")
    private String imgUrl;

    //String -> Address 수정 (0502 임태경)
    //Column명 각각 지정하는 방식으로 변경 (0502 임태경)
    @AttributeOverride(name = "address", column = @Column(name = "branch_address"))
    @AttributeOverride(name = "extraAddress", column = @Column(name = "branch_extra_address"))
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
    private BranchOperationInfo branchOperationInfo;

    @Builder
    public Branch(String name, String imgUrl, Address address, String phone, BranchStatus status, BranchOperationInfo branchOperationInfo) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.branchOperationInfo = branchOperationInfo;
    }

    public boolean delete(Long branchId) {
        if (branchId.equals(this.id)) {
            this.status = BranchStatus.OUT;
            return true;
        }
        return false;
    }

    public boolean update(Long branchId, BranchUpdateDTO updateDTO, String imgUrl) {
        if (branchId.equals(this.id)) {
            this.phone = updateDTO.getPhone();

            LocalTime openTime = TimeUtils.stringParseLocalTime(updateDTO.getOpenTime());
            LocalTime closeTime = TimeUtils.stringParseLocalTime(updateDTO.getCloseTime());
            this.branchOperationInfo.update(openTime, closeTime);

            if (!imgUrl.isEmpty()) {
                this.imgUrl = imgUrl;
            }

            return true;
        }

        return false;
    }
}
