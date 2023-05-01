package ssginc_kdt_team3.BE.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "owner")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Owner{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private long id;

    @NotNull
    @Column(name = "owner_email" , length = 50)
    private String email;

    @NotNull
    @Column(name = "owner_password",length = 20)
    private String password;

    @NotNull
    @Column(name = "owner_name",length = 10)
    private String name;

    @NotNull
    @Column(name = "owner_phone" , length = 20)
    private String phone;

    @Column(name="owner_birthday")
    private LocalDate birthday;

    @Column(name = "owner_gender")
    private boolean gender;

    @Column(name="owner_address")
    private String address;

    @Column(name="owner_role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "owner_status")
    private UserStatus status;

    @OneToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

}
