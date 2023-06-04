package ssginc_kdt_team3.BE.controller.branch;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.branch.BranchShopDTO;
import ssginc_kdt_team3.BE.DTOs.customer.ReviewResponseDTO;
import ssginc_kdt_team3.BE.DTOs.shop.ShopDetailDTO;
import ssginc_kdt_team3.BE.domain.Branch;
import ssginc_kdt_team3.BE.domain.User;
import ssginc_kdt_team3.BE.service.customer.CustomerPersonalizeShopService;
import ssginc_kdt_team3.BE.service.shop.ShopDetailReviewService;
import ssginc_kdt_team3.BE.service.shop.ShopDetailService;
import ssginc_kdt_team3.BE.service.branch.BranchShopListService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/shop")
public class BranchShopController {

    private final BranchShopListService branchShopListService;
    private final ShopDetailService detailService;
    private final ShopDetailReviewService reviewService;
    private final CustomerPersonalizeShopService personalizeShopService;
    //0604 신영 추가 : 고객 맞춤 매장 추천

    @GetMapping("/list/{id}")
    public ResponseEntity<List<BranchShopDTO>> branchShopList(@PathVariable("id")Long BranchId) throws Exception {

            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
                    .body(branchShopListService.BranchShop(BranchId));
    //고객이 지점내 매장조회
    }
    @PostMapping("list/{branch_id}/{user_id}")
    public ResponseEntity<List<BranchShopDTO>> branchRecommendShop(@PathVariable("branch_id")Long BranchId,@PathVariable("user_id")Long UserId){

        List<BranchShopDTO> personalizeShop = personalizeShopService.customerPersonalizeShop(BranchId, UserId);

            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(personalizeShop);

    }//0604 신영 추가 : 고객 맞춤 매장 추천

    @GetMapping("/detail/{id}")
    public ResponseEntity ShopDetail(@PathVariable("id") Long id) throws Exception {
        ShopDetailDTO shopDetailDTO = detailService.ShopDetailList(id);

        if (shopDetailDTO != null) {
            return ResponseEntity.ok(shopDetailDTO);
        }
        return ResponseEntity.badRequest().build();
    }

    // 매장 상세페이지 하단 고객후기 추가
    @GetMapping("/detail/review/{id}/{page}")
    public ResponseEntity ShopDetialAndReviews(@PathVariable(name ="id") Long shopId, @PathVariable(name = "page") int page){
        Pageable pageable = PageRequest.of(page-1, 6);
        ResponseEntity<Page<ReviewResponseDTO>> response = ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(reviewService.ShopDetailAndReviews(shopId, pageable));
        return response;

    }
}