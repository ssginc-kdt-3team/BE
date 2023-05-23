package ssginc_kdt_team3.BE.service.shop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.DTOs.shop.ShopDetailDTO;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.repository.shop.JpaDataShopRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopDetailService {
    private final JpaDataShopRepository shopRepository;

    public ShopDetailDTO ShopDetailList(Long shopId) {

        Optional<Shop> optShop = shopRepository.findById(shopId);

        if (optShop.isPresent()) {
            Shop shop = optShop.get();

            ShopDetailDTO shopDetailDTO = new ShopDetailDTO(shop);
            return shopDetailDTO;

        }
        return null;
    }

}
