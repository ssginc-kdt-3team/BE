package ssginc_kdt_team3.BE.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.coupon.CreateCouponDTO;
import ssginc_kdt_team3.BE.domain.Coupon;
import ssginc_kdt_team3.BE.service.coupon.CouponManagementService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/coupons")
public class AdminCouponController {

  private final CouponManagementService couponService;

  // 쿠폰 생성
  @PostMapping("/add")
  public ResponseEntity<Coupon> addCoupon(@RequestBody CreateCouponDTO couponDTO, @RequestParam Long adminId){
    Coupon coupon = couponService.createCoupon(couponDTO, adminId);

    if(coupon == null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.status(HttpStatus.OK).body(coupon);
  }

}
