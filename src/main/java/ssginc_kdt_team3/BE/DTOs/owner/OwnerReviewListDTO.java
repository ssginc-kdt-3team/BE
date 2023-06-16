package ssginc_kdt_team3.BE.DTOs.owner;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.Review;
import ssginc_kdt_team3.BE.enums.ReviewStatus;
import ssginc_kdt_team3.BE.util.TimeUtils;

@Data
@NoArgsConstructor
public class OwnerReviewListDTO {
  private String userName;
  private String title;
  private String contents;
  private int point;
  private ReviewStatus reviewStatus;
  private String writeTime;

  public OwnerReviewListDTO(Review review) {
    this.userName = review.getReservation().getCustomer().getName();
    this.title = review.getTitle();
    this.contents = review.getContents();
    this.point = review.getPoint();
    this.reviewStatus = review.getStatus();
    this.writeTime = TimeUtils.localDataTimeParseString(review.getTime());
  }
}
