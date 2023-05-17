package ssginc_kdt_team3.BE.DTOs.customer;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Embedded;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;


@Data
public class CustomerJoinDTO {
    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;
    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String password;
    @NotEmpty(message = "이름은 필수 입력 값입니다.")
    private String name;
    @NotEmpty(message = "연락처는 필수 입력 값입니다.")
    private String phone;
    private LocalDate birthday;

    @Embedded
    private Address address;
    private Boolean gender;

}
