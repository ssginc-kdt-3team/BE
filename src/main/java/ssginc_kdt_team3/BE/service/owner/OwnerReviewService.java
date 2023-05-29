package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerReviewListDTO;
import ssginc_kdt_team3.BE.domain.Review;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
import ssginc_kdt_team3.BE.repository.review.JpaDataReviewRepository;
import ssginc_kdt_team3.BE.util.TimeUtils;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OwnerReviewService {

  private final JpaDataReviewRepository reviewRepository;
  private final JpaDataOwnerRepository ownerRepository;

  // 점주: 후기목록 조회 -필터, 페이지 처리, 고객 DELETE 항목도 보임
  public Page<OwnerReviewListDTO> getReviewList(String type, Long ownerId, Pageable pageRequest, LocalDateTime start, LocalDateTime end) {

    // 필터1. type: 높은 순(pointDesc), 낮은 순(pointAsc) 정렬
    if(type.equals("pointDesc")){
      Page<Review> pointAsc = reviewRepository.findAllByReservation_Shop_Owner_IdOrderByPointDesc(ownerId, pageRequest);
      return convertDto(pointAsc);

    } else if (type.equals("pointAsc")){
      Page<Review> pointDesc = reviewRepository.findAllByReservation_Shop_Owner_IdOrderByPointAsc(ownerId, pageRequest);
      return convertDto(pointDesc);

    } else if (type.equals("date")) {
      // 필터2. 달력에 기간 정해서 조회, LocalDate :start, end
//      LocalDate startDate = start.toLocalDate();
//      LocalDate endDate = end.toLocalDate();

      Page<Review> timeBetween = reviewRepository.findAllByReservation_Shop_Owner_IdAndTimeBetween(ownerId, start, end, pageRequest);
      System.out.println("timeBetween ===========" + timeBetween);
      return convertDto(timeBetween);
    }
    // 기본 페이지: 작성시간 최신순 정렬
    Page<Review> defaultPage = reviewRepository.findAllByReservation_Shop_Owner_Id(ownerId, pageRequest);
    return convertDto(defaultPage);
  }

  // Page로 받은 Review 엔티티를 DTO로 변환
  private Page<OwnerReviewListDTO> convertDto(Page<Review> reviews) {
    List<OwnerReviewListDTO> reviewDTOList = new ArrayList<>();

    for(Review r : reviews){
      OwnerReviewListDTO reviewDTO = new OwnerReviewListDTO(r);
      reviewDTOList.add(reviewDTO);
    }
    return new PageImpl<>(reviewDTOList, reviews.getPageable(), reviews.getTotalElements());
  }


}
