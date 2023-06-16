package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Admin {

    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
