package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import lombok.Setter;
import ssginc_kdt_team3.BE.DTOs.cust.Address;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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


  //LocalDateTime -> LocalDate로 수정 (0502 임태경)
  //birthdate -> birthday로 수정 (0502 임태경)
  @Column(name = "cust_birthday")
  private LocalDate birthday;

  //Column명 각각 지정하는 방식으로 변경 (0502 임태경)
  @AttributeOverride(name = "city", column = @Column(name = "cust_city"))
  @AttributeOverride(name = "district", column = @Column(name = "cust_district"))
  @AttributeOverride(name = "detail", column = @Column(name = "cust_detail"))
  @AttributeOverride(name = "zipCode", column = @Column(name = "cust_zipCode"))
  private Address address;


  //Role -> UserRole로 수정 (0502 임태경)
  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "cust_role")
  private UserRole role;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "cust_status")
  private UserStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "grade_id")
  private Grade grade;
}
