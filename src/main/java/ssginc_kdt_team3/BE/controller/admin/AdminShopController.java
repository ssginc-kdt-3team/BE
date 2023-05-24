package ssginc_kdt_team3.BE.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.shop.AdminShopDetailDTO;
import ssginc_kdt_team3.BE.DTOs.shop.AdminShopUpdateDTO;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.service.admin.AdminShopService;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/shop")
public class AdminShopController {

    private final AdminShopService shopService;
    //
    @GetMapping("/findAll/{id}/{page}")
    public ResponseEntity<Page<Shop>> findAllCustomer(@PathVariable(name = "id") Long storeId,  @PathVariable(name = "page") int page) {
        Pageable pageable = PageRequest.of(page-1, 5);
        ResponseEntity<Page<Shop>> response = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(shopService.findAllShop(storeId, pageable));

        return response;
    }

    @GetMapping("/findOne/{id}")
    public ResponseEntity<Shop> findOne(@PathVariable(name = "id") Long shopId) {

        Optional<Shop> shopById = shopService.findShopById(shopId);

        if (shopById.isPresent()) {
            Shop shop = shopById.get();

            ResponseEntity<Shop> response = ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(shop);

            return response;
        }
        return null;
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<AdminShopDetailDTO> showShopDetail(@PathVariable(name = "id") Long shopId) {
        Optional<AdminShopDetailDTO> oneShop = shopService.findOneShop(shopId);

        if (oneShop.isPresent()) {
            AdminShopDetailDTO shopDetailDTO = oneShop.get();
            return ResponseEntity.ok(shopDetailDTO);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/updateOne/{id}")
    public boolean updateOne(@PathVariable(name = "id") Long shopId, @RequestBody AdminShopUpdateDTO updateDTO) {

        return shopService.updateShop(shopId, updateDTO);
    }
}