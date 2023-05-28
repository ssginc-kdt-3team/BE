package ssginc_kdt_team3.BE.repository.charging;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

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
}