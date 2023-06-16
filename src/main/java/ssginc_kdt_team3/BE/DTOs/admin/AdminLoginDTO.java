package ssginc_kdt_team3.BE.DTOs.admin;

import lombok.Data;
import javax.validation.constraints.NotEmpty;


@Data
public class AdminLoginDTO {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
