package ssginc_kdt_team3.BE.DTOs.admin;

import lombok.Data;
import ssginc_kdt_team3.BE.enums.UserStatus;



@Data
public class AdminBranchOwnerDTO {

    private Long ownerId;
    private String ownerName;
    private String ownerPhone;
    private UserStatus userStatus;
    private String shopLocation;
    private String shopName;
    private String branchName;

    public AdminBranchOwnerDTO(Long id, String ownerName, String ownerPhone, UserStatus userStatus, String shopName, String shopLocation, String branchName) {
        this.ownerId = id;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.userStatus = userStatus;
        this.shopName = shopName;
        this.shopLocation = shopLocation;
        this.branchName = branchName;
    }
    public AdminBranchOwnerDTO(){

    }

}