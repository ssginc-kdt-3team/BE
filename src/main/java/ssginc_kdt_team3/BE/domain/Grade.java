package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import lombok.Setter;
import ssginc_kdt_team3.BE.enums.GradeType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
public class Grade {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "grade_id")
  private Long id;

  @NotNull
  @Column(name = "grade_name")
  @Enumerated(EnumType.STRING)
  private GradeType name;

  @NotNull
  @Column(name = "grade_rate")
  private Double rate;

  //주석처리 처리 (0502 임태경)
  //  @NotNull
  //  @Column(name = "grade_history")
  //  private String history;

//  @OneToMany(mappedBy = "grade")
//  private List<Cust> cust;

}
