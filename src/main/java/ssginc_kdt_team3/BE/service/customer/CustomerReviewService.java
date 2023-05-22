package ssginc_kdt_team3.BE.service.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.customer.ReviewAddRequestDTO;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.domain.Review;
import ssginc_kdt_team3.BE.enums.ReviewStatus;
import ssginc_kdt_team3.BE.repository.customer.JpaDataCustomerRepository;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;
import ssginc_kdt_team3.BE.repository.review.JpaDataReviewRepository;

import java.time.LocalDateTime;


import static ssginc_kdt_team3.BE.enums.ReservationStatus.DONE;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomerReviewService {
  private final JpaDataCustomerRepository customerRepository;
  private final JpaDataReservationRepository reservationRepository;
  private final JpaDataReviewRepository reviewRepository;


  public boolean addMyReview(ReviewAddRequestDTO reviewDTO) {
    // 로그인한 고객만 가능: 존재하는 고객인지 검증
    Customer customer = customerRepository.findById(reviewDTO.getUserId()).orElse(null);

    // 난 필드값 중 id가 필요한데 customer 자체랑 예약 id를 비교하고 있어, equal 스트링 비교, 값만 비교하면 되니까 ==
    // 리뷰의 고객id와 예약의 고객id 일치 && ReserveStatus.DONE
    Reservation reservation = reservationRepository.findById(reviewDTO.getReservationId()).orElse(null);
    if(customer.getId() == reservation.getCustomer().getId() && reservation.getStatus().equals(DONE)){
      LocalDateTime writeTime = LocalDateTime.now();
      LocalDateTime localDateTime = reservation.getReservationDate().plusDays(7);// DTO에서 주지 않는 값은 레포지토리에서 가져와서 검증

      // reservationDate + 7일 >= writeTime
      if(writeTime.isBefore(localDateTime)){

        // reviewRepository에 넣으려면 Review 상태야야 돼. 레포에는 엔티티로 선언한 것만 넣을 수 있으니까 DTO -> Review로 변경
        Review review = new Review();
        review.setTitle(reviewDTO.getTitle());
        review.setContents(reviewDTO.getContents());
        review.setTime(writeTime);
        review.setPoint(review.getPoint());
        review.setStatus(ReviewStatus.SHOW); // 최초상태
        review.setReservation(reservation);
        reviewRepository.save(review);
        return true;
      }
    }
    return false;
  }


}
