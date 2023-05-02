package ssginc_kdt_team3.BE.DTOs.cust;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ssginc_kdt_team3.BE.domain.Grade;
import ssginc_kdt_team3.BE.enums.UserStatus;

@Getter
@Setter
@AllArgsConstructor
public class CustListDTO {

    private Long id;

    private String name;

    private String email;

    private UserStatus status;

    private Grade grade;
}
