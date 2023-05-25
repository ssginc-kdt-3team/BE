package ssginc_kdt_team3.BE.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.admin.AdminReviewListDTO;
import ssginc_kdt_team3.BE.domain.Review;
import ssginc_kdt_team3.BE.service.admin.AdminReviewService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminManagementController {
  private final AdminReviewService reviewService;

  @GetMapping("/reviewList/{type}/{id}/{page}")
  public ResponseEntity<Page<AdminReviewListDTO>> showReviewList(@PathVariable(name = "type") String type,
                                       @PathVariable(name = "id") Long branchId,
                                       @PathVariable(name = "id") Long shopId,
                                       @PathVariable(name = "page") int page){
    PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("time").descending());
    Page<AdminReviewListDTO> reviewList = reviewService.getReviewList(type, branchId, shopId, pageRequest);
    return ResponseEntity.ok(reviewList);
  }
}
