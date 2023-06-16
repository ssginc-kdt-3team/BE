package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.customer.ReviewResponseDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerReviewListDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerReviewService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owner")
public class OwnerReviewController {
  private final OwnerReviewService reviewService;

  @PostMapping("/reviewList/{type}/{id}/{page}")
  public ResponseEntity<Page<OwnerReviewListDTO>> showReviewList(@PathVariable(name = "type") String type,
                                                                 @PathVariable(name = "id") Long ownerId,
                                                                 @PathVariable(name = "page") int page,
                                                                 @RequestBody Map<String, LocalDate> request){
    LocalDateTime start = request.get("start").atStartOfDay();
    LocalDateTime end = request.get("end").atTime(LocalTime.MAX);
    System.out.println("start==================> " + start);
    System.out.println("end==================> " + end);

    PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("time").descending());
    Page<OwnerReviewListDTO> reviewList = reviewService.getReviewList(type, ownerId, pageRequest, start, end);
    System.out.println("reviewList ==============> " + reviewList);

    return ResponseEntity.ok(reviewList);
  }
}
