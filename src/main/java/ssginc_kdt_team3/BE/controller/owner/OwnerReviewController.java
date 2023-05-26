package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerReviewListDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerReviewService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owner")
public class OwnerReviewController {
  private final OwnerReviewService reviewService;

  @GetMapping("/reviewList/{id}/{page}")
  public ResponseEntity<Page<OwnerReviewListDTO>> showReviewList(@PathVariable(name = "id") Long ownerId,
                                                                 @PathVariable(name = "page") int page){
    PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("time").descending());
    Page<OwnerReviewListDTO> reviewList = reviewService.getReviewList(ownerId, pageRequest);
    return ResponseEntity.ok(reviewList);
  }
}
