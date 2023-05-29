package ssginc_kdt_team3.BE.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.repository.grade.GradeRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminGradeService {
  private final GradeRepository gradeRepository;


}
