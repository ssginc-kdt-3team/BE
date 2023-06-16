package ssginc_kdt_team3.BE.DTOs.customer;


import lombok.*;
import ssginc_kdt_team3.BE.enums.GradeType;
import ssginc_kdt_team3.BE.enums.UserStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerListDTO {

    private Long id;

    private String name;

    private String email;

    private UserStatus status;

    private GradeType grade; //0506 이현: grade enum 추가로 기존 String grade 수정

}
