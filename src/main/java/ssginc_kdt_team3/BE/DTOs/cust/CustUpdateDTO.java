package ssginc_kdt_team3.BE.DTOs.cust;

import lombok.Data;
import ssginc_kdt_team3.BE.domain.Grade;
import ssginc_kdt_team3.BE.enums.UserStatus;

@Data
public class CustUpdateDTO {

    private String password;

    private String name;

    private String phone;

    private Address address;

    private UserStatus status;

    private Grade grade;
}
