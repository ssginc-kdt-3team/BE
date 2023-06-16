package ssginc_kdt_team3.BE.repository.grade;

import org.springframework.data.jpa.repository.JpaRepository;
import ssginc_kdt_team3.BE.domain.Grade;
import ssginc_kdt_team3.BE.enums.GradeType;

public interface GradeRepository extends JpaRepository<Grade, Long> {
  Grade findByName(GradeType name);


}
