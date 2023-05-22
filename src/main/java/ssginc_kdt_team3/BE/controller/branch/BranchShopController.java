package ssginc_kdt_team3.BE.controller.branch;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.shop.ShopDetailWithReviewResponseDTO;
import ssginc_kdt_team3.BE.domain.Review;
import ssginc_kdt_team3.BE.service.Shop.ShopDetailService;
import ssginc_kdt_team3.BE.service.admin.branch.BranchShopListService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/shop")
public class BranchShopController {

    private final BranchShopListService branchshop;

    private final ShopDetailService detailService;

    @GetMapping("/list/")
    public ResponseEntity<String> branchShopList(@RequestParam("id") long id) throws Exception {
        try {
            String branchShopJSON = branchshop.BranchShop(id);
            return new ResponseEntity<>(branchShopJSON, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/detail/{id}")
    public ResponseEntity ShopDetail(@PathVariable("id") Long id) throws Exception {
        ShopDetailWithReviewResponseDTO retDTO = detailService.ShopDetailList(id);

        if (retDTO != null) {
            return ResponseEntity.ok(retDTO);
        }
        return ResponseEntity.badRequest().build();

//        try {
//            String shopDetail = (String) shopDetailDTO;
//            return new ResponseEntity<>(shopDetail, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
    }
//    @GetMapping("/review/{id}/{id}")
//    public ResponseEntity<Page<Review>> PageReview(@PathVariable("id"))
//
//}

}