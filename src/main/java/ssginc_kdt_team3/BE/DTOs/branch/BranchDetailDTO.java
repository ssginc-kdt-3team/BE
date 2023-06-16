package ssginc_kdt_team3.BE.DTOs.branch;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.DTOs.Address;
import ssginc_kdt_team3.BE.domain.Branch;
import ssginc_kdt_team3.BE.enums.BranchStatus;

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

    public BranchDetailDTO(Branch branch) {
        this.id = branch.getId();
        this.branchImgUrl = branch.getImgUrl();
        this.address = branch.getAddress();
        this.name = branch.getName();
        this.phone = branch.getPhone();
        this.status = branch.getStatus();


        String openTime = branch.getBranchOperationInfo().getOpenTime().toString();
        String closeTime = branch.getBranchOperationInfo().getCloseTime().toString();
        String openDay = branch.getBranchOperationInfo().getOpenDay().toString();

        if (branch.getBranchOperationInfo().getOutDay() == null) {
            this.outDay = null;
        } else {
            String outDay = branch.getBranchOperationInfo().getOutDay().toString();
            this.outDay = outDay;
        }

        this.openTime = openTime;
        this.closeTime = closeTime;
        this.openDay = openDay;

    }
}
