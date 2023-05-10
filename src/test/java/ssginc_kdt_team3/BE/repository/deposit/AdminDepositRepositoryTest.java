package ssginc_kdt_team3.BE.repository.deposit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.deposit.AdminDepositDTO;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Reservation;

import java.util.List;

@SpringBootTest
public class AdminDepositRepositoryTest {

    @Autowired
    DepositRepository repository;

    @Test
    public void DepositList() {
        //w

        //t
        List<Deposit> depositList = repository.findShopDepositList(1L);
        System.out.println(depositList.size());
    }

    @Test
    public void DepositList2() {
        List<Deposit> depositList = repository.findBranchDepositList(2L);
        System.out.println(depositList.size());
    }
}
