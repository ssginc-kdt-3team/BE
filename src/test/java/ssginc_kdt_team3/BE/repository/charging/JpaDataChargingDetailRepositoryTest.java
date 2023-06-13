package ssginc_kdt_team3.BE.repository.charging;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.domain.ChargingDetail;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JpaDataChargingDetailRepositoryTest {

    @Autowired
    JpaDataChargingDetailRepository detailRepository;

    @Test
    public void findSum() {
        int sumCharging = detailRepository.findSumCharging(15L);
        System.out.println(sumCharging);

    }

    @Test
    public void 환불_가능_여부_확인() {
        List<ChargingDetail> allByChargingManagementIdOrderByOperateDateDesc = detailRepository.findChargingManagementUsingLog(5L);
        for ( ChargingDetail c : allByChargingManagementIdOrderByOperateDateDesc) {
            System.out.println("================");
            System.out.println(c.isStatus());
            System.out.println(c.getValue());
            System.out.println("================");
        }
    }

    @Test
    public void 잔액_조회() {
        List<Object[]> objects = detailRepository.balanceInquiry(1L);
        for (Object[] l : objects) {
            Long s =(Long) l[0];
            Long s1 =(Long) l[1];

            System.out.println("id = " + s);
            System.out.println("value = " + s1);
        }
    }
}