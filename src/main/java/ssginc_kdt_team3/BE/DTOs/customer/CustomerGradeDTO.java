package ssginc_kdt_team3.BE.DTOs.customer;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.Grade;
import ssginc_kdt_team3.BE.enums.GradeType;

@Data
@NoArgsConstructor
public class CustomerGradeDTO {
  private GradeType name;
  private Double rate;
  private int requirement;

  public CustomerGradeDTO(Grade grade) {
    this.name = grade.getName();
    this.rate = grade.getRate();
    this.requirement = grade.getRequirement();
  }
}
