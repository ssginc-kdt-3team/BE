package ssginc_kdt_team3.BE.DTOs.branch;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.Branch;
import ssginc_kdt_team3.BE.enums.BranchStatus;

@Data
@NoArgsConstructor
public class BranchAdminListDTO {

    private Long id;

    private String name;

    private String openTime;

    private String closeTime;

    private String phone;

    private BranchStatus status;

    public BranchAdminListDTO(Branch branch) {
        this.id = branch.getId();
        this.name = branch.getName();
        this.openTime = branch.getBranchOperationInfo().getOpenTime().toString();
        this.closeTime = branch.getBranchOperationInfo().getCloseTime().toString();
        this.phone = branch.getPhone();
        this.status = branch.getStatus();
    }
}
