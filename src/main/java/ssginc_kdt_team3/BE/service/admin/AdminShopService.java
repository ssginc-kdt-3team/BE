package ssginc_kdt_team3.BE.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.shop.AdminShopUpdateDTO;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.domain.ShopOperationInfo;
import ssginc_kdt_team3.BE.enums.ShopStatus;
import ssginc_kdt_team3.BE.repository.shop.JpaDataShopOperationInfoRepository;
import ssginc_kdt_team3.BE.repository.shop.JpaDataShopRepository;

import java.time.LocalTime;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminShopService {

    private final JpaDataShopRepository shopRepository;
    private final JpaDataShopOperationInfoRepository shopOperationInfoRepository;

    public Page<Shop> findAllShop(Long storeId, Pageable pageable) {
        log.info("service id = {}", storeId);
        return shopRepository.findAllByStoreId(storeId, pageable);
    }

    public Optional<Shop> findShopById(Long shopId) {
        return shopRepository.findById(shopId);
    }

    public boolean updateShop(Long shopId, AdminShopUpdateDTO adminShopUpdateDTO) {
        Optional<Shop> byId = shopRepository.findById(shopId);

        if (byId.isPresent()) {
            Shop shop = byId.get();
            ShopOperationInfo operationInfo = shopOperationInfoRepository.findById(shop.getOperationInfo().getId()).get();
            String shopName = adminShopUpdateDTO.getShopName();
            String businessName = adminShopUpdateDTO.getBusinessName();
            String shopInfo = adminShopUpdateDTO.getShopInfo();
            String shopLocation = adminShopUpdateDTO.getShopLocation();
            ShopStatus shopStatus = adminShopUpdateDTO.getShopStatus();
            String shopImgUrl = adminShopUpdateDTO.getShopImgUrl();
            LocalTime openTime = adminShopUpdateDTO.getOpenTime();
            LocalTime closeTime = adminShopUpdateDTO.getCloseTime();
            LocalTime orderClose = adminShopUpdateDTO.getOrderClose();

            shop.setName(shopName);
            shop.setBusinessName(businessName);
            shop.setInfo(shopInfo);
            shop.setLocation(shopLocation);
            shop.setStatus(shopStatus);
            shop.setShopImgUrl(shopImgUrl);

            operationInfo.setOpenTime(openTime);
            operationInfo.setCloseTime(closeTime);
            operationInfo.setOrderCloseTime(orderClose);

            shopRepository.save(shop);
            shopOperationInfoRepository.save(operationInfo);

            return true;
        } else {
            return false;
        }
    }
}
