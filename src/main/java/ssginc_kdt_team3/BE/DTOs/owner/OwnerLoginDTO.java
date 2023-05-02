package ssginc_kdt_team3.BE.DTOs.owner;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
@Setter
@Getter
@Data
public class OwnerLoginDTO {
    @NotEmpty(message = "아이디를 입력해주세요.")
    @Email(message = "이메일을 다시 확인해주세요.")
    private String email;


    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Length(min = 8, max = 16, message = "비밀번호를 다시 입력해주세요.")
    private String password;

}