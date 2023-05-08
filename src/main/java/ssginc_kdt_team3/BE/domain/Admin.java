package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import lombok.Setter;
import ssginc_kdt_team3.BE.enums.UserRole;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Admin {

    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name ="admin_login_id")
    @NotNull
    private String loginId;

    @Column(name ="admin_password")
    @NotNull
    private String password;

//    @Column(name = "admin_role")
//    @Enumerated(EnumType.STRING)
//    private UserRole role;
}
