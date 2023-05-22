package ssginc_kdt_team3.BE.DTOs.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.domain.Review;
import ssginc_kdt_team3.BE.enums.ReservationStatus;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReviewDTO {
//  private Long id; //리뷰 id
  private Long reservationId; //예약 id
  private Long userId;
  private String title;
  private String contents;
  private int point;
//  private ReservationStatus ReserveStatus;
//  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
//  private LocalDateTime reservationDate; 이건 화면에 뿌릴게 아니니까 안 줘도 돼

  public ReviewDTO(Review review, Reservation reservation) {
    this.reservationId = reservation.getId();
    this.userId = reservation.getCustomer().getId();
    this.title = review.getTitle();
    this.contents = review.getContents();
    this.point = review.getPoint();
  }
}
