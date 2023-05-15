package ssginc_kdt_team3.BE.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter @AllArgsConstructor @NoArgsConstructor
public class Customer extends User{

  @NotNull
  @Column(name = "customeromer_alarm")
  private boolean alarmBoolean;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "grade_id")
  private Grade grade;
}
