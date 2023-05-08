package ssginc_kdt_team3.BE.DTOs.cust;

import lombok.Data;

@Data
public class PwdUpdateDTO {
    private String password;
    private String newPassword;
    private String newPasswordConfirm;
}
