package ssginc_kdt_team3.BE.DTOs.admin;

import ssginc_kdt_team3.BE.DTOs.customer.Address;
import ssginc_kdt_team3.BE.enums.UserStatus;

import java.time.LocalDate;

public class AdminOwnerDetailDTO {
    Long ownerId;

    String branchName;

    String shopName;

    String ownerName;

    String ownerPhone;

    String shopLocation;

    String ownerEmail;

    boolean ownerGender;

    Address ownerAddress;

    LocalDate ownerBirthday;

    UserStatus ownerStatus;
}
