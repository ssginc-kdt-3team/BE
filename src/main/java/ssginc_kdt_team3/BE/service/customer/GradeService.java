package ssginc_kdt_team3.BE.service.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.repository.interfaces.Customer.GradeRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GradeService {

  private final GradeRepository gradeRepository;

//  public Grade findGrade(Long CustomerId, GradeDTO gradeDTO) {
//    Optional<Grade> findId = gradeRepository.findById(CustomerId);
//
//    findId.
//
//
//  }




}
