package ssginc_kdt_team3.BE.service.pointManagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PointManagementServiceTest {

    @Autowired
    PointManagementService pointManagementService;

    @Test
    public void 보유_포인트_테스트() {
        int sumPoint = pointManagementService.showCustomerPointValue(100L);
        System.out.println(sumPoint);
    }
}