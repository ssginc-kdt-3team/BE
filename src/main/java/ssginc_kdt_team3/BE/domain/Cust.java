package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import lombok.Setter;
import ssginc_kdt_team3.BE.DTOs.cust.Address;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.DTOs.cust.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Cust {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cust_id")
  private Long id;

  @NotNull
  @Column(name = "cust_email")
  private String email;

  @NotNull
  @Column(name = "cust_password")
  private String password;

  @NotNull
  @Column(name = "cust_name")
  private String name;

  @NotNull
  @Column(name = "cust_phone")
  private String phone;

  @Column(name = "cust_gender")
  private Boolean gender;

  @Column(name = "cust_birthdate")
  private LocalDateTime birthdate;

  @Column(name = "cust_address")
  private Address address;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "cust_role")
  private Role role;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "cust_status")
  private UserStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "grade_id")
  private Grade grade;
}
