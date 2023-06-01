package ssginc_kdt_team3.BE.service.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.coupon.CustomerCouponListDTO;
import ssginc_kdt_team3.BE.domain.CouponProvide;
import ssginc_kdt_team3.BE.enums.CouponStatus;
import ssginc_kdt_team3.BE.repository.coupon.CouponProvideRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerCouponService {

  private final CouponProvideRepository provideRepository;

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
