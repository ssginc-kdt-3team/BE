package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import lombok.Setter;

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
  private String name;
  @NotNull
  @Column(name = "grade_rate")
  private Double rate;
  @NotNull
  @Column(name = "grade_history")
  private String history;
  @OneToMany(mappedBy = "grade")
  private List<Cust> cust;

}
