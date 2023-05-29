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
        int sumCharging = detailRepository.findSumCharging(1L);
        System.out.println(sumCharging);

    }

    @Test
    public void 환불_가능_여부_확인() {
        List<ChargingDetail> allByChargingManagementIdOrderByOperateDateDesc = detailRepository.findAllByChargingManagement_IdOrderByOperateDateDesc(5L);
        for ( ChargingDetail c : allByChargingManagementIdOrderByOperateDateDesc) {
            System.out.println("================");
            System.out.println(c.isStatus());
            System.out.println(c.getValue());
            System.out.println("================");
        }
    }
}