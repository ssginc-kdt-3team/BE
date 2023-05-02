package ssginc_kdt_team3.BE.DTOs.cust;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ssginc_kdt_team3.BE.domain.Grade;
import ssginc_kdt_team3.BE.enums.UserStatus;

@Getter
@Setter
public class CustListDTO {

    private Long id;

    private String name;

    private String email;

    private UserStatus status;

    private String grade;

    public CustListDTO(Long id, String name, String email, UserStatus status, String grade) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
        this.grade = grade;
    }

    public CustListDTO() {
    }
}
