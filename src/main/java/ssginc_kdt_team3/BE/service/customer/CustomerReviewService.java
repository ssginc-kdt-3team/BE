package ssginc_kdt_team3.BE.service.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.customer.ReviewAddRequestDTO;
import ssginc_kdt_team3.BE.DTOs.customer.ReviewResponseDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerReviewListDTO;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.domain.Review;
import ssginc_kdt_team3.BE.enums.ReviewStatus;
import ssginc_kdt_team3.BE.repository.customer.JpaDataCustomerRepository;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;
import ssginc_kdt_team3.BE.repository.review.JpaDataReviewRepository;
import ssginc_kdt_team3.BE.service.pointManagement.PointManagementService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static ssginc_kdt_team3.BE.enums.ReservationStatus.DONE;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomerReviewService {
  private final JpaDataCustomerRepository customerRepository;
  private final JpaDataReservationRepository reservationRepository;
  private final JpaDataReviewRepository reviewRepository;
  private final PointManagementService pointManagementService;


  public boolean addMyReview(ReviewAddRequestDTO reviewDTO) {
    // 존재하는 고객인지 검증
    Customer customer = customerRepository.findById(reviewDTO.getUserId()).orElse(null);

    // 리뷰의 고객id와 예약의 고객id 일치 && ReserveStatus.DONE
    Reservation reservation = reservationRepository.findById(reviewDTO.getReservationId()).orElse(null);
    if(customer.getId() == reservation.getCustomer().getId() && reservation.getStatus().equals(DONE)){
      LocalDateTime writeTime = LocalDateTime.now();
      LocalDateTime localDateTime = reservation.getReservationDate().plusDays(7);

      // reservationDate + 7일 >= writeTime
      if(writeTime.isBefore(localDateTime) && reviewDTO.getPoint() >=1){

        // DTO -> Review 변경: Review 생성
        Review review = new Review();
        review.setTitle(reviewDTO.getTitle());
        review.setContents(reviewDTO.getContents());
        review.setTime(writeTime);
        review.setPoint(reviewDTO.getPoint());
        review.setStatus(ReviewStatus.SHOW); // 최초상태
        review.setReservation(reservation);
        reviewRepository.save(review);

        saveReviewPoint(customer, writeTime);

        return true;
      }
    }
    return false;
  }

  private void saveReviewPoint(Customer customer, LocalDateTime writeTime) {
    pointManagementService.getPointSave(true, 200, "리뷰 작성", customer, writeTime);
  }

  public boolean deleteMyReview(Long reviewId) {
    // 2) 본인이 작성한 후기가 맞는지 검증
    Review review = reviewRepository.findById(reviewId).orElse(null);

    // 프론트에서 reviewId만 보내주는데 customer 검증을 어떻게 해
    if(review.getStatus().equals(ReviewStatus.SHOW)){
      review.setStatus(ReviewStatus.DELETE); // 3) status.SHOW -> DELETE 변경

      // 4) 저장
      reviewRepository.save(review);
      return true;
    }
    return false;
  }

  // 마이페이지: 본인이 작성한 모든 후기 조회
  public Page<ReviewResponseDTO> getReviewList(Long userId, Pageable pageRequest) {
    Page<Review> allReviews = reviewRepository.findAllByStatusAndReservation_Customer_Id(ReviewStatus.SHOW, userId, pageRequest);
    return convertDto(allReviews);
  }

  // Page로 받은 Review 엔티티를 DTO로 변환
  private Page<ReviewResponseDTO> convertDto(Page<Review> reviews) {
    List<ReviewResponseDTO> reviewDTOList = new ArrayList<>();

    for(Review r : reviews){
      ReviewResponseDTO reviewDTO = new ReviewResponseDTO(r);
      reviewDTOList.add(reviewDTO);
    }
    return new PageImpl<>(reviewDTOList, reviews.getPageable(), reviews.getTotalElements());
  }



}
