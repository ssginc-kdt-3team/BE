package ssginc_kdt_team3.BE.DTOs.cust;

import lombok.Data;
import ssginc_kdt_team3.BE.domain.Grade;
import ssginc_kdt_team3.BE.enums.UserStatus;

import javax.persistence.Embedded;

@Data
public class CustUpdateDTO {

    /*
    * 0503 임태경 admin이 cust 정보 변경할 때 사용하는 DTO
    * */

    private String name;

    private String password;

    private String phone;

    @Embedded
    private Address address;

    private UserStatus status;

    private Grade grade;

}
