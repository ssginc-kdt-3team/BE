package ssginc_kdt_team3.BE.DTOs.admin;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class AdminBranchOwnerDTO {

    private Long ownerId;

    private String branchName;

    private String shopName;

    private String ownerName;

    private String ownerPhone;

    private String shopLocation;

    public AdminBranchOwnerDTO(){

    }
}
