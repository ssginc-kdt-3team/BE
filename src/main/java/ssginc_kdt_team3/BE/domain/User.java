package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import ssginc_kdt_team3.BE.DTOs.cust.Address;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Column(name = "user_email", unique = true, nullable = false, length = 20)
    @NotBlank
    @Email(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @Length(max = 20)
    protected String email;

    @Column(name = "user_password", nullable = false, length = 16)
    @NotBlank
    @Length(min = 8, max = 16)
    protected String password;

    @Column(name = "user_name", nullable = false, length = 10)
    @NotBlank
    protected String name;

    @Column(name = "user_phone", nullable = true, length = 12)
    protected String phoneNumber;

    //LocalDateTime -> LocalDate로 수정 (0502 임태경)
    //birthdate -> birthday로 수정 (0502 임태경)
    @Column(name = "user_birthday")
    protected LocalDate birthday;

    @Column(name = "user_gender")
    protected Boolean gender;

    //Column명 각각 지정하는 방식으로 변경 (0502 임태경)
    @AttributeOverride(name = "city", column = @Column(name = "user_city"))
    @AttributeOverride(name = "district", column = @Column(name = "user_district"))
    @AttributeOverride(name = "detail", column = @Column(name = "user_detail"))
    @AttributeOverride(name = "zipCode", column = @Column(name = "user_zipCode"))
    protected Address address;

    @NotNull
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    protected UserRole role;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    protected UserStatus status;

}
