package ssginc_kdt_team3.BE.DTOs.owner;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OwnerNewPwDTO {

    @NotEmpty(message = "새로운 비밀번호를 입력해주세요.")
    @Length(min = 8, max = 16, message = "비밀번호를 다시 입력해주세요.")
    private String newPassword1;

    @NotEmpty(message = "비밀번호를 재입력해주세요.")
    @Length(min = 8, max = 16, message = "비밀번호를 다시 입력해주세요.")
    private String newPassword2;

}
