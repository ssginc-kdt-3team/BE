package ssginc_kdt_team3.BE.service.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.coupon.CustomerCouponListDTO;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerGradeDTO;
import ssginc_kdt_team3.BE.domain.CouponProvide;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Grade;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.CouponStatus;
import ssginc_kdt_team3.BE.enums.GradeType;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.coupon.CouponProvideRepository;
import ssginc_kdt_team3.BE.repository.customer.JpaDataCustomerRepository;
import ssginc_kdt_team3.BE.repository.grade.GradeRepository;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Component
public class CustomerGradeService {
  private final JpaDataCustomerRepository customerRepository;
  private final JpaDataReservationRepository reservationRepository;
  private final GradeRepository gradeRepository;

  // 등급조회
  public CustomerGradeDTO getMyGrade(Long userId){

    // 로그인한 고객 id로 등급정보 가져오기
    Customer customer = customerRepository.findById(userId).orElse(null);
    Grade grade = customer.getGrade();
    CustomerGradeDTO gradeDTO = new CustomerGradeDTO(grade);

    return gradeDTO;
    }

  // 등급 변동
  public GradeType getGradeChange(Long userId){
    // 1. userId로 해당하는 고객을 찾은 다음, 3개월 전부터 오늘까지의 예약횟수를 조회한다.
    Customer customer = customerRepository.findById(userId).orElse(null);
    System.out.println("customer====="+ customer);
    List<Reservation> reservationDone = reservationRepository.findAllByCustomer_IdAndStatusAndReservationDateBetween(customer.getId(), ReservationStatus.DONE, LocalDateTime.now().minusMonths(3), LocalDateTime.now());
    List<Reservation> reservationNoShow = reservationRepository.findAllByCustomer_IdAndStatusAndReservationDateBetween(customer.getId(), ReservationStatus.NOSHOW, LocalDateTime.now().minusMonths(3), LocalDateTime.now());

    System.out.println("reservationDone==" +reservationDone.size());
    System.out.println("reservationNoShow==" + reservationNoShow.size());

    // 2. 예약횟수가 조건 충족하면 grade 변경
    if (reservationDone.size()-reservationNoShow.size() >= 10) {
      customer.setGrade(gradeRepository.findByName(GradeType.Gold));
      customer.setGradeChangeDate(LocalDate.now());
      Customer save = customerRepository.save(customer);
      return save.getGrade().getName();

    }else if(reservationDone.size()-reservationNoShow.size() >= 5) {
      customer.setGrade(gradeRepository.findByName(GradeType.Green));
      customer.setGradeChangeDate(LocalDate.now());
      Customer save = customerRepository.save(customer);
      return save.getGrade().getName();

    } else {
      customer.setGrade(gradeRepository.findByName(GradeType.Welcome));
      customer.setGradeChangeDate(LocalDate.now());
      Customer save = customerRepository.save(customer);
      return save.getGrade().getName();
    }
  }

  @Scheduled(cron = "0 0 0 * * *") // grade 변경되면 3개월 뒤 00시 00분 00초에 등급조건 다시 확인
  public void gradeCheck(){
    // gradeChangeDate가 오늘부터 3개월 전과 일치하는 값
    List<Customer> allByGradeChangeDate = customerRepository.findAllByGradeChangeDate(LocalDate.now().minusMonths(3));

    // List 고객을 각자 조건에 맞는 등급 재조정
    for(Customer c : allByGradeChangeDate) {
      getGradeChange(c.getId());
      System.out.println("스케줄 테스트 ==== 성공");
    }
  }


}


