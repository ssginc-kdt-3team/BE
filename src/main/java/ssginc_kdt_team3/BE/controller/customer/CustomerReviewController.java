package ssginc_kdt_team3.BE.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.customer.ReviewAddRequestDTO;
import ssginc_kdt_team3.BE.service.customer.CustomerReviewService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/review")
public class CustomerReviewController {
  private final CustomerReviewService reviewService;

  // 후기 등록
  @PostMapping("/add")
  public ResponseEntity addMyReview(@RequestBody ReviewAddRequestDTO reviewDTO){
    try {
      boolean result = reviewService.addMyReview(reviewDTO);
      return ResponseEntity.status(HttpStatus.OK).body(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  // 내가 작성한 전체 후기목록 조회
//  @PostMapping("/get")
//  public void getMyReview(){
//    return;
//  }
//
  @PostMapping("/delete/{id}")
  public ResponseEntity deleteMyReview(@PathVariable(name = "id") Long reviewId){

      boolean resultTrue = reviewService.deleteMyReview(reviewId);

      if(resultTrue){ //이미 얘가 값을 가진애라 검증 불필요
        return ResponseEntity.status(HttpStatus.OK).body(resultTrue);
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();


  } // 서비스 결과는 true, false 주니까 컨트롤러에서는 바꼈는지 알려줘야 돼



}
