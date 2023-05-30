package ssginc_kdt_team3.BE.DTOs.customer;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.Review;
import ssginc_kdt_team3.BE.util.TimeUtils;

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
  private String writeTime; // TimeUtils 사용해서 형식지정 및 String 타입으로 변경

  public ReviewResponseDTO(Review review) {
    this.reviewId = review.getId();
    this.userId = review.getReservation().getCustomer().getId();
    this.userName = review.getReservation().getCustomer().getName();
    this.title = review.getTitle();
    this.contents = review.getContents();
    this.point = review.getPoint();
    this.writeTime = TimeUtils.localDataTimeParseString(review.getTime());
  }
}
