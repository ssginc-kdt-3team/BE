package ssginc_kdt_team3.BE.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.coupon.CustomerCouponListDTO;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerGradeDTO;
import ssginc_kdt_team3.BE.domain.Grade;
import ssginc_kdt_team3.BE.enums.GradeType;
import ssginc_kdt_team3.BE.service.customer.CustomerMyPageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("customer")
public class CustomerMyPageController {

  private final CustomerMyPageService myPageService;

  @GetMapping("/coupon/{id}")
  public ResponseEntity showCouponList(@PathVariable(name = "id") Long userId){
    List<CustomerCouponListDTO> myCoupon = myPageService.getMyCoupon(userId);

    if(myCoupon == null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("보유한 쿠폰이 없습니다.");
    }
    return ResponseEntity.status(HttpStatus.OK).body(myCoupon);
  }

  @GetMapping("/grade/{id}")
  public ResponseEntity showGradeInfo(@PathVariable(name = "id") Long userId){
    CustomerGradeDTO myGrade = myPageService.getMyGrade(userId);
    return ResponseEntity.status(HttpStatus.OK).body(myGrade);
  }

  @GetMapping("/gradeChange/{id}")
  public ResponseEntity showGradeChange(@PathVariable(name = "id") Long userId){
    GradeType gradeChange = myPageService.getGradeChange(userId);
    System.out.println("gradeChange" + gradeChange);
    return ResponseEntity.status(HttpStatus.OK).body(gradeChange);
  }





}
