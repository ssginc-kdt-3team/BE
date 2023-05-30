package ssginc_kdt_team3.BE.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.coupon.CustomerCouponListDTO;
import ssginc_kdt_team3.BE.domain.CouponProvide;
import ssginc_kdt_team3.BE.service.customer.CustomerCouponService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("customer/coupon")
public class CustomerCouponController {

  private final CustomerCouponService couponService;

  @GetMapping("/{id}")
  public ResponseEntity showCouponList(@PathVariable(name = "id") Long userId){
    List<CustomerCouponListDTO> myCoupon = couponService.getMyCoupon(userId);

    if(myCoupon == null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("보유한 쿠폰이 없습니다.");
    }
    return ResponseEntity.status(HttpStatus.OK).body(myCoupon);
  }





}
