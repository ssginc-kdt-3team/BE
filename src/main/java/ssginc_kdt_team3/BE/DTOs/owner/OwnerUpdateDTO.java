package ssginc_kdt_team3.BE.DTOs.owner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.DTOs.customer.Address;
import ssginc_kdt_team3.BE.enums.UserStatus;
import javax.persistence.Embedded;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OwnerUpdateDTO {

    private String name;

    private String phone;

    private String city;
    private String district;
    private String detail;
    private String zipCode;


}
