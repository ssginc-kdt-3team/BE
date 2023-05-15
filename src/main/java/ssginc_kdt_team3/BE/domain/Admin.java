package ssginc_kdt_team3.BE.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ssginc_kdt_team3.BE.enums.UserRole;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class Admin{

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

    @Column(name = "admin_role")
    @Enumerated(EnumType.STRING)
    private UserRole roles;

//    @ElementCollection(fetch = FetchType.EAGER) //roles 컬렉션
//    @Builder.Default
//    private List<String> roles = new ArrayList<>();
}
