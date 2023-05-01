package ssginc_kdt_team3.BE.DTOs.admin;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AdminLoginDTO {
    @NotEmpty
    @Max(20)
    private String id;

    @NotEmpty
    @Max(12)
    private String password;
}
