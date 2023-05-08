package ssginc_kdt_team3.BE.DTOs.customer;

import lombok.Data;
@Data
public class CustomerFindDTO {
    private String email;
    private String password;
    private String name;
    private String phone;

}
