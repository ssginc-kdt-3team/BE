package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.admin.AdminReviewListDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerReviewListDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.domain.Review;
import ssginc_kdt_team3.BE.enums.ReviewStatus;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
import ssginc_kdt_team3.BE.repository.review.JpaDataReviewRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OwnerReviewService {

  private final JpaDataReviewRepository reviewRepository;
  private final JpaDataOwnerRepository ownerRepository;

  // 점주: 후기목록 조회 -필터, 페이지 처리
  public Page<OwnerReviewListDTO> getReviewList(Long ownerId, Pageable pageRequest) {

    // 로그인한 사용자가 점주인지 확인: 점주 = 본인의 매장 후기만 조회 가능해야 한다.
//    Owner owner = ownerRepository.findById(ownerId).orElse(null);

// ownerId == shopId 하고 싶은데 ?
      Page<Review> allReviews = reviewRepository.findAllByReservation_Shop_Owner_Id(ownerId, pageRequest);
      return convertDto(allReviews);
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
