package ssginc_kdt_team3.BE.repository.interfaces.cust;

import org.springframework.data.repository.CrudRepository;
import ssginc_kdt_team3.BE.domain.Grade;

public interface GradeRepository extends CrudRepository<Grade, Long> {
  // save
  // saveAll
  // findById
  // existsById
  // findAll
  // findAllById
  // long count : 전체 엔티티 개수 확인

}
