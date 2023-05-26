package ssginc_kdt_team3.BE.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.admin.AdminReviewListDTO;
import ssginc_kdt_team3.BE.domain.Review;
import ssginc_kdt_team3.BE.repository.review.JpaDataReviewRepository;
import ssginc_kdt_team3.BE.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminReviewService {

  private final JpaDataReviewRepository reviewRepository;

  // 관리자: 후기목록 조회 -필터, 페이지 처리
  public Page<AdminReviewListDTO> getReviewList(String type, Long branchId, Long shopId, Pageable pageRequest){

    // 1. 지점 선택: 지점의 전체 후기 가져온다.
    if(type.equals("branch")){
        Page<Review> allReviews = reviewRepository.findAllByReservation_Shop_Branch_Id(branchId, pageRequest);
        return convertDto(allReviews);

    } else if(type.equals("shop")){
      // 2. 매장 선택: 해당 매장의 모든 후기 가져온다.
      Page<Review> allReviewsByShop = reviewRepository.findAllByReservation_Shop_Id(shopId, pageRequest);
      return convertDto(allReviewsByShop);
    }
    return null;
  }

  // Page로 받은 Review 엔티티를 DTO로 변환
  private Page<AdminReviewListDTO> convertDto(Page<Review> reviews) {
    List<AdminReviewListDTO> reviewDTOList = new ArrayList<>();

    for(Review r : reviews){
      AdminReviewListDTO reviewDTO = new AdminReviewListDTO(r);
      reviewDTOList.add(reviewDTO);
    }
    return new PageImpl<>(reviewDTOList, reviews.getPageable(), reviews.getTotalElements());
  }


}
