package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import ssginc_kdt_team3.BE.enums.UserRole;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name ="admin_email", length = 20, nullable = false)
    private String email;

    @Column(name ="admin_password", length = 12, nullable = false)
    private String password;

    @Column(name = "admin_role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name ="admin_lastLoginTime", nullable = true)
    private LocalDateTime lastLoginTime;

    @Column(name ="admin_lastLoginIp", nullable = true)
    private String lastLoginIp;
}
