package ssginc_kdt_team3.BE.DTOs.owner;

import lombok.AllArgsConstructor;
import lombok.Data;
import ssginc_kdt_team3.BE.DTOs.customer.Address;
import ssginc_kdt_team3.BE.enums.UserStatus;
import javax.persistence.Embedded;
import java.time.LocalDate;

@AllArgsConstructor
@Data
public class OwnerUpdateDTO {

    String name;

    String password;

    LocalDate birthday;

    private String phone;

    @Embedded
    private Address adddress;

    private UserStatus userStatus;

    public OwnerUpdateDTO(){

    }
}
