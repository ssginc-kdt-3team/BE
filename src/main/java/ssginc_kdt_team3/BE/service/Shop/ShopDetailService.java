package ssginc_kdt_team3.BE.service.Shop;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.DTOs.customer.ReviewResponseDTO;
import ssginc_kdt_team3.BE.DTOs.shop.ShopDetailWithReviewResponseDTO;
import ssginc_kdt_team3.BE.domain.Review;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.repository.review.JpaDataReviewRepository;
import ssginc_kdt_team3.BE.repository.shop.JpaDataShopRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopDetailService {

    private final JpaDataShopRepository shopRepository;

    private final JpaDataReviewRepository reviewRepository;

    public ShopDetailWithReviewResponseDTO ShopDetailList(Long shopId) throws JsonProcessingException {

        Optional<Shop> optShop = shopRepository.findById(shopId);

        if (optShop.isPresent()) {
            Shop shop = optShop.get();
            // 매개변수는 외부에서 넘어오는, 호출할 때 넘겨주는 값이야
            // 리뷰페이지 만들어서 담자: 리뷰의 예약의 샵.id = shop.getID ->
            // 리스트 -> dtoList -> Page
            // 해당 shop 의 모든 review

//            List<Review> findReviewList = reviewRepository.findReviewByShopId(shopId);
            List<Review> allByReservationShopId = reviewRepository.findAllByReservation_ShopId(shopId);
            List<ReviewResponseDTO> reviewResponseDTOList = new ArrayList<>();
            for (Review review : allByReservationShopId){
                System.out.println("review reviewreviewreviewreviewreviewreview = " + review.getTitle());
                reviewResponseDTOList.add(new ReviewResponseDTO(review));
            }



//            for (Review review : findReviewList) {
//                System.out.println("review = " + review);
//
//            }

            Page<ReviewResponseDTO> reviewResponseDTOPage = new PageImpl<>(reviewResponseDTOList);

            ShopDetailWithReviewResponseDTO retDTO = new ShopDetailWithReviewResponseDTO(shop, reviewResponseDTOPage);

            System.out.println("retDTO = " + retDTO);

            return retDTO;
        }

        return null;
    }
}
