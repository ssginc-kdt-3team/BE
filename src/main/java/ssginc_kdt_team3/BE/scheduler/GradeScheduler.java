package ssginc_kdt_team3.BE.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.repository.customer.JpaDataCustomerRepository;
import ssginc_kdt_team3.BE.service.customer.CustomerGradeService;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GradeScheduler {
  private final CustomerGradeService gradeService;
  private final JpaDataCustomerRepository customerRepository;

  @Scheduled(cron = "0 0 0 * * *") // grade 변경되면 3개월 뒤 00시 00분 00초에 등급조건 다시 확인
  public void gradeCheck(){
    // gradeChangeDate가 오늘부터 3개월 전과 일치하는 값
    List<Customer> allByGradeChangeDate = customerRepository.findAllByGradeChangeDate(LocalDate.now().minusMonths(3));

    // List 고객을 각자 조건에 맞는 등급 재조정
    for(Customer c : allByGradeChangeDate) {
      gradeService.getGradeChange(c.getId());
      System.out.println("스케줄 테스트 ==== 성공");
    }
  }
}
