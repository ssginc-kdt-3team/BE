package ssginc_kdt_team3.BE.DTOs.owner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
