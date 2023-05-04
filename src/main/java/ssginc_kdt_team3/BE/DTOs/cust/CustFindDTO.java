package ssginc_kdt_team3.BE.DTOs.cust;

import lombok.Data;
import ssginc_kdt_team3.BE.domain.Grade;
import ssginc_kdt_team3.BE.enums.UserStatus;

@Data
public class CustFindDTO {
    private String email;
    private String password;
    private String name;
    private String phone;

}
