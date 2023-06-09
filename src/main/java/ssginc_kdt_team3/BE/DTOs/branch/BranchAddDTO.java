package ssginc_kdt_team3.BE.DTOs.branch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.DTOs.Address;

import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class BranchAddDTO {

//    @NotNull
//    private MultipartFile branchImg;

    @NotNull
    @Embedded
    private Address address;

    @NotNull
    private String name;

    @NotNull
    private String phone;

    @NotNull
    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$")
    private String openTime;

    @NotNull
    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$")
    private String closeTime;

    @NotNull
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
    private String openDay;
}
