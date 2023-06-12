package ssginc_kdt_team3.BE.repository.customer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.shop.ShopDetailDTO;
import ssginc_kdt_team3.BE.repository.menu.JpaDataShopMenuRepository;
import ssginc_kdt_team3.BE.repository.shop.JpaDataShopRepository;
import ssginc_kdt_team3.BE.service.shop.ShopDetailService;

@Slf4j
@SpringBootTest
public class ShopDetailTest {

    @Autowired
    JpaDataShopRepository repo;

    @Autowired
    JpaDataShopMenuRepository repo2;

    @Autowired
    ShopDetailService ser = new ShopDetailService(repo,repo2);

    @Test
    public void ShopDetail() throws Exception{
        Long num = 1L;

        ShopDetailDTO shopDetailDTO = ser.ShopDetailList(num);

        log.info("shopId = {}",shopDetailDTO.getShopId());
        log.info("shopName = {}",shopDetailDTO.getShopName());
        log.info("shopLocation = {}",shopDetailDTO.getShopLocation());


    }

}
