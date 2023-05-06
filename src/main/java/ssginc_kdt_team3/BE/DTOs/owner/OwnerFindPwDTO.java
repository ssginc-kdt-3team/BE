package ssginc_kdt_team3.BE.DTOs.owner;

import lombok.Data;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class OwnerFindPwDTO {
    @NotEmpty(message = "이메일은 필수 입력값 입니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private String email;
    @NotEmpty(message = "이름은 필수 입력값입니다.")
    private String name;
    @NotEmpty(message = "전화번호는 필수 입력값입니다.")
    private String phone;
}