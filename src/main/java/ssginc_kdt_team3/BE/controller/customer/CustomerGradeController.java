package ssginc_kdt_team3.BE.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerGradeDTO;
import ssginc_kdt_team3.BE.enums.GradeType;
import ssginc_kdt_team3.BE.service.customer.CustomerCouponService;
import ssginc_kdt_team3.BE.service.customer.CustomerGradeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("customer/grade")
public class CustomerGradeController {
  private final CustomerGradeService gradeService;

  // 등급조회
  @GetMapping("/{id}")
  public ResponseEntity showGradeInfo(@PathVariable(name = "id") Long userId){
    CustomerGradeDTO myGrade = gradeService.getMyGrade(userId);
    return ResponseEntity.status(HttpStatus.OK).body(myGrade);
  }

  // 등급 변동
  @GetMapping("/change/{id}")
  public ResponseEntity showGradeChange(@PathVariable(name = "id") Long userId){
    GradeType gradeChange = gradeService.getGradeChange(userId);
    System.out.println("gradeChange" + gradeChange);
    return ResponseEntity.status(HttpStatus.OK).body(gradeChange);
  }





}
