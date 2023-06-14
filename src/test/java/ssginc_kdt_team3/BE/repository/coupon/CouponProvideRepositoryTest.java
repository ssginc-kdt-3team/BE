package ssginc_kdt_team3.BE.repository.coupon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.domain.CouponProvide;
import ssginc_kdt_team3.BE.enums.CouponStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CouponProvideRepositoryTest {

    @Autowired
    CouponProvideRepository couponProvideRepository;

    @Test
    void 쿠폰_리스트() {
        List<CouponProvide> allByStatusAndCustomerIdOrderByGivenDay = couponProvideRepository.findAllByStatusAndCustomer_IdOrderByGivenDay(CouponStatus.GIVEN, 20L);
        System.out.println(allByStatusAndCustomerIdOrderByGivenDay.size());
    }

}