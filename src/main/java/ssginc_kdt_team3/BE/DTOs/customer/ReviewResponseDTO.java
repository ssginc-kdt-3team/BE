package ssginc_kdt_team3.BE.DTOs.customer;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.domain.Review;

@Data
@NoArgsConstructor
public class ReviewResponseDTO {
  private Long userId;
  private String title;
  private String contents;
  private int point;

  public ReviewResponseDTO(Review review) {
    this.userId = review.getReservation().getCustomer().getId();
    this.title = review.getTitle();
    this.contents = review.getContents();
    this.point = review.getPoint();
  }
}
