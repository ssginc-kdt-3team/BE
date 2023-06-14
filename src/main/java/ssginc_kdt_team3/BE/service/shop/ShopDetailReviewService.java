package ssginc_kdt_team3.BE.service.shop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.DTOs.customer.ReviewResponseDTO;
import ssginc_kdt_team3.BE.domain.Review;
import ssginc_kdt_team3.BE.repository.review.JpaDataReviewRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopDetailReviewService {

    private final JpaDataReviewRepository reviewRepository;

    public Page<ReviewResponseDTO> ShopDetailAndReviews(Long shopId, Pageable pageable) {

        // 매장 상세페이지 하단 고객후기
        //리스트 -> dtoList -> Page, 해당 shop 의 모든 review 가져오기

        List<Review> allByReservationShopId = reviewRepository.findAllByReservation_ShopIdOrderByIdDesc(shopId);
        List<ReviewResponseDTO> reviewResponse = new ArrayList<>();
        for (Review review : allByReservationShopId) {
            System.out.println("review =============> " + review.getTitle());
            reviewResponse.add(new ReviewResponseDTO(review));
        }
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), reviewResponse.size());

        Page<ReviewResponseDTO> activePage = new PageImpl<>(reviewResponse.subList(start, end), pageable, reviewResponse.size());
        return activePage;
    }
}



