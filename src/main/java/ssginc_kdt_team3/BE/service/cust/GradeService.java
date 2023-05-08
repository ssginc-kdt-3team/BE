package ssginc_kdt_team3.BE.service.cust;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.repository.interfaces.cust.GradeRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GradeService {

  private final GradeRepository gradeRepository;

//  public Grade findGrade(Long custId, GradeDTO gradeDTO) {
//    Optional<Grade> findId = gradeRepository.findById(custId);
//
//    findId.
//
//
//  }




}
