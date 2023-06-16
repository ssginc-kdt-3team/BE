package ssginc_kdt_team3.BE.DTOs.customer;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.domain.Review;

@Data
@NoArgsConstructor
public class ReviewAddRequestDTO { // 고객 후기 등록용 DTO
  private Long reservationId; //예약 id
  private Long userId;
  private String title;
  private String contents;
  private int point;

  public ReviewAddRequestDTO(Review review, Reservation reservation) {
    this.reservationId = reservation.getId();
    this.userId = reservation.getCustomer().getId();
    this.title = review.getTitle();
    this.contents = review.getContents();
    this.point = review.getPoint();
  }
}
