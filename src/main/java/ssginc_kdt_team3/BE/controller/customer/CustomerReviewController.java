package ssginc_kdt_team3.BE.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.customer.ReviewDTO;
import ssginc_kdt_team3.BE.service.customer.CustomerReviewService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/review")
public class CustomerReviewController {

  private final CustomerReviewService reviewService;

  // 후기 등록
  @PostMapping("/add")
  public ResponseEntity addMyReview(@RequestBody ReviewDTO reviewDTO){
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
//  @PostMapping("/delete ")
//  public void deleteMyReview(){
//    return;
//  }


}
