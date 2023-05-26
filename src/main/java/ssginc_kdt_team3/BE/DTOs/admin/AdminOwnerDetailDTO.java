package ssginc_kdt_team3.BE.DTOs.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.DTOs.Address;
import ssginc_kdt_team3.BE.enums.UserStatus;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminOwnerDetailDTO {
    Long ownerId;

    String ownerEmail;

    String ownerName;

    boolean ownerGender;

    Address ownerAddress;

    String ownerPhone;

    LocalDate ownerBirthday;

    UserStatus ownerStatus;

    String shopName;

    String shopLocation;

    String branchName;


}
