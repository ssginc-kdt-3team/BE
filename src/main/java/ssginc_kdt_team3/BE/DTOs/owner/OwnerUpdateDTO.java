package ssginc_kdt_team3.BE.DTOs.owner;

import lombok.AllArgsConstructor;
import lombok.Data;
import ssginc_kdt_team3.BE.DTOs.cust.Address;

import javax.persistence.Embedded;
@AllArgsConstructor
@Data
public class OwnerUpdateDTO {

    private String phone;

    @Embedded
    private Address adddress;

    private UserStatus userStatus;

    public OwnerUpdateDTO(){

    }
}
