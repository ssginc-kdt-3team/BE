package ssginc_kdt_team3.BE.DTOs.owner;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

@Data
public class LoginOwnerDTO {
    @NotEmpty(message = "아이디를 입력해주세요.")
    @Email(message = "이메일을 다시 확인해주세요.")
    private String email;


    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Length(min = 8, max = 16, message = "비밀번호를 다시 입력해주세요.")
    private String password;

}
