package ssginc_kdt_team3.BE.service.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.repository.shop.JpaDataShopRepository;
import ssginc_kdt_team3.BE.service.Shop.ShopDetailService;

@SpringBootTest
public class ShopDetailTest {
    @Autowired
    JpaDataShopRepository repo;
    @Autowired
    private final ShopDetailService ser = new ShopDetailService(repo);

//    @Test
//    public void ShopDetail() throws Exception{
//        int num = 2;
//        long num2 = num;
//        String result = ser.ShopDetailList(num2);
//        System.out.println(result);
//    }

}
