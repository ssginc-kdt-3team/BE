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
public class Customer extends User{

  @NotNull
  @Column(name = "customer_alarm")
  private boolean alarmBoolean;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "grade_id")
  private Grade grade;
}
