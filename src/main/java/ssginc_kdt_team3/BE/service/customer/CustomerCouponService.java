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
public class CustomerCouponService {
  private final CouponProvideRepository provideRepository;

  // 쿠폰목록 조회
  public List<CustomerCouponListDTO> getMyCoupon(Long userId){
    List<CouponProvide> findCoupon = provideRepository.findAllByStatusAndCustomer_Id(CouponStatus.GIVEN, userId);

    List<CustomerCouponListDTO> result = new ArrayList<>();
    for(CouponProvide provide : findCoupon){
      CustomerCouponListDTO customerCoupon = new CustomerCouponListDTO(provide);
      result.add(customerCoupon);
    }
    return result;
  }

}


