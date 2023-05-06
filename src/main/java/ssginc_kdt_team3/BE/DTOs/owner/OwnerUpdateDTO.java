package ssginc_kdt_team3.BE.DTOs.owner;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ssginc_kdt_team3.BE.DTOs.cust.Address;
import ssginc_kdt_team3.BE.enums.UserStatus;

import javax.persistence.Embedded;

@Data
public class OwnerUpdateDTO {

    private String phone;

    @Embedded
    private Address adddress;

    private UserStatus userStatus;
}
