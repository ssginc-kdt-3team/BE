package ssginc_kdt_team3.BE.DTOs.admin;

import ch.qos.logback.core.util.TimeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.Review;
import ssginc_kdt_team3.BE.enums.ReviewStatus;
import ssginc_kdt_team3.BE.util.TimeUtils;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AdminReviewListDTO {
  // 관리자 후기 목록 조회용

  // 타입으로 프론트에서 넘겨주면, 어느 매장을 조회할지에 대한 전달값....1번 매장을 조회하려면, id를
  private String branchName;
  private String shopName;
  private String userName;
  private String title;
  private String contents;
  private int point;
  private ReviewStatus reviewStatus;
  private String writeTime;

  public AdminReviewListDTO(Review review) {
    this.branchName = review.getReservation().getShop().getBranch().getName();
    this.shopName = review.getReservation().getShop().getName();
    this.userName = review.getReservation().getCustomer().getName();
    this.title = review.getTitle();
    this.contents = review.getContents();
    this.point = review.getPoint();
    this.reviewStatus = review.getStatus();
    this.writeTime = TimeUtils.localDataTimeParseString(review.getTime());
  }
}
