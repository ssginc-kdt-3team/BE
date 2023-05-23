package ssginc_kdt_team3.BE.DTOs.customer;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.Review;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReviewResponseDTO { // ShopDetailService
  private Long reviewId;
  private Long userId;
  private String userName;
  private String title;
  private String contents;
  private int point;
  private LocalDateTime writeTime;

  public ReviewResponseDTO(Review review) {
    this.reviewId = review.getId();
    this.userId = review.getReservation().getCustomer().getId();
    this.userName = review.getReservation().getCustomer().getName();
    this.title = review.getTitle();
    this.contents = review.getContents();
    this.point = review.getPoint();
    this.writeTime = LocalDateTime.now();
  }
}
