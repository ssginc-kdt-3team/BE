package ssginc_kdt_team3.BE.domain;

import lombok.*;
import ssginc_kdt_team3.BE.enums.CustomerType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter @AllArgsConstructor @NoArgsConstructor
public class Customer extends User{

  @NotNull
  @Column(name = "customeromer_alarm")
  private boolean alarmBoolean;

  @NotNull
  @Column(name = "customeromer_type")
  @Enumerated(EnumType.STRING)
  private CustomerType customerType;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "grade_id")
  private Grade grade;
}
