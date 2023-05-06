package ssginc_kdt_team3.BE.DTOs.owner;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
@Getter
@Setter
@Data
public class OwnerChangePwDTO {
    @NotEmpty(message = "이메일은 필수 입력값 입니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private String email;

    @NotEmpty(message = "현재 비밀번호를 입력해주세요.")
    @Length(min = 8, max = 16, message = "비밀번호를 다시 입력해주세요.")
    private String nowPassword;

    @NotEmpty(message = "새로운 비밀번호를 입력해주세요.")
    @Length(min = 8, max = 16, message = "비밀번호를 다시 입력해주세요.")
    private String newPassword1;

    @NotEmpty(message = "비밀번호를 재입력해주세요.")
    @Length(min = 8, max = 16, message = "비밀번호를 다시 입력해주세요.")
    private String newPassword2;
}
