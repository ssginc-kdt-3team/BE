package ssginc_kdt_team3.BE.DTOs.owner;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ssginc_kdt_team3.BE.DTOs.cust.Address;
import ssginc_kdt_team3.BE.enums.UserStatus;

import javax.persistence.Embedded;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
public class OwnerUpdateDTO {

    private String name;

    private String password;

    private String phone;

    private LocalDate birthday;

    @Embedded
    private Address adddress;

    private UserStatus userStatus;
}
