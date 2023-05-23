package ssginc_kdt_team3.BE.DTOs.branch;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.DTOs.customer.Address;
import ssginc_kdt_team3.BE.domain.Branch;
import ssginc_kdt_team3.BE.domain.BranchOperationInfo;
import ssginc_kdt_team3.BE.enums.BranchStatus;
import ssginc_kdt_team3.BE.util.TimeUtils;

@Data
@NoArgsConstructor
public class BranchDetailDTO {

    private Long id;

    private String branchImgUrl;

    private Address address;

    private String name;
    private String phone;

    private BranchStatus status;

    private String openTime;
    private String closeTime;

    private String openDay;
    private String outDay;

    public BranchDetailDTO(Branch branch, BranchOperationInfo info) {
        this.id = branch.getId();
        this.branchImgUrl = branch.getImgUrl();
        this.address = branch.getAddress();
        this.name = branch.getName();
        this.phone = branch.getPhone();
        this.status = branch.getStatus();

//        TimeUtils.

        this.openTime = openTime;
        this.closeTime = closeTime;
        this.openDay = openDay;
        this.outDay = outDay;
    }
}
