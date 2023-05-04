package ssginc_kdt_team3.BE.DTOs.cust;

import lombok.Data;
import ssginc_kdt_team3.BE.enums.UserStatus;

@Data
public class PwdUpdateDTO {
    private String password;
    private String newPassword;
    private String newPasswordConfirm;
}
