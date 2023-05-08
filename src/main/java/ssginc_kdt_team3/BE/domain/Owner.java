package ssginc_kdt_team3.BE.domain;

import com.sun.istack.NotNull;
import lombok.*;

import ssginc_kdt_team3.BE.DTOs.cust.Address;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;


import javax.persistence.*;
import java.time.LocalDate;

@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "owner")
@Getter
@Setter
@ToString

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

    //String -> Address로 수정 (0502 임태경)
    //Column명 각각 지정하는 방식으로 변경 (0502 임태경)
    @AttributeOverride(name = "city", column = @Column(name = "owner_city"))
    @AttributeOverride(name = "district", column = @Column(name = "owner_district"))
    @AttributeOverride(name = "detail", column = @Column(name = "owner_detail"))
    @AttributeOverride(name = "zipCode", column = @Column(name = "owner_zipCode"))
    private Address address;

    @Column(name="owner_role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "owner_status")
    private UserStatus status;


}
