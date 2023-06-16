package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ssginc_kdt_team3.BE.enums.CustomerType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
public class Customer extends User{

  @NotNull
  @Column(name = "customeromer_alarm")
  private boolean alarmBoolean;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "grade_id")
  private Grade grade;

  @Column(name = "customer_type")
  @Enumerated(EnumType.STRING)
  private CustomerType type;

  // 0531 이현: 고객 등급변동일 추가
  @Column(name = "customer_gradeDate")
  private LocalDate gradeChangeDate;

  //0604 : 신영 ->고객 나이 추가
  @Column(name = "user_age")
  protected int age;
}
