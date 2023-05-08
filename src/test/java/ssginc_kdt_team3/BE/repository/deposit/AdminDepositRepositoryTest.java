package ssginc_kdt_team3.BE.repository.deposit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.deposit.AdminDepositDTO;
import ssginc_kdt_team3.BE.domain.Deposit;

import java.util.List;

@SpringBootTest
public class AdminDepositRepositoryTest {

    @Autowired
    AdminDepositRepository repository;

    @Test
    public void DepositList() {
        List<Deposit> depositList = repository.findDepositList(1L);
        System.out.println(depositList.get(0).getOrigin_value());
    }
}
