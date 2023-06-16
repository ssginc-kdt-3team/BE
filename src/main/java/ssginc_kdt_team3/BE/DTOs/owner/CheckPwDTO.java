package ssginc_kdt_team3.BE.DTOs.owner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CheckPwDTO {
    @NotEmpty(message = "이메일은 필수 입력값 입니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private String email;
    @NotEmpty(message = "이름은 필수 입력값입니다.")
    private String name;
    @NotEmpty(message = "전화번호는 필수 입력값입니다.")
    private String phone;
    @NotEmpty(message = "비밀번호는 필수 입니다.")
    @Length(min = 8, max = 16, message = "비밀번호는 8자이상 16자 이하로 입력해주세요.")
    private String password;

}
