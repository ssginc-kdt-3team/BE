package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ssginc_kdt_team3.BE.DTOs.shop.OwnerShopDetailDTO;
import ssginc_kdt_team3.BE.DTOs.shop.OwnerShopUpdateDTO;
import ssginc_kdt_team3.BE.DTOs.shop.ShopAddDTO;
import ssginc_kdt_team3.BE.service.shop.OwnerShopService;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/owner/shop")
@RequiredArgsConstructor
public class OwnerShopController {

    private final OwnerShopService ownerShopService;

    @PostMapping("/add")
    public ResponseEntity createNewBranch(@Validated @RequestPart(value = "shopData") ShopAddDTO dto, BindingResult bindingResult,
                                          @RequestPart(value = "shopImg") MultipartFile shopImg, @RequestPart(value = "businessImg") MultipartFile businessImg) {

        if (bindingResult.hasErrors()) {
            log.info("error");
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        Optional<Long> aLong = null;
        try {
            aLong = ownerShopService.addNewShop(dto, businessImg, shopImg);

            if (aLong.isPresent()) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());

        } catch (IOException e) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<OwnerShopDetailDTO> showOwnerShopDetail(@PathVariable(name = "id") Long ownerId) {
        Optional<OwnerShopDetailDTO> ownerShopDetailDTO = ownerShopService.showShopDetail(ownerId);

        if (ownerShopDetailDTO.isPresent()) {
            OwnerShopDetailDTO result = ownerShopDetailDTO.get();
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateOwnerShop(@PathVariable(name = "id") Long shopId, @RequestPart(value = "shopData") OwnerShopUpdateDTO dto,
                                                  @RequestPart(value = "shopImg") MultipartFile shopImg) {
        boolean b = ownerShopService.updateShop(shopId, dto, shopImg);

        if (b) {
            return ResponseEntity.ok("정상적으로 업데이트 되었습니다.");
        }
        return ResponseEntity.badRequest().body("업데이트 실패");
    }
}
