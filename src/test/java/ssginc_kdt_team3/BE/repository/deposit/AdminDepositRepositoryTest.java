package ssginc_kdt_team3.BE.repository.deposit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.relational.core.sql.In;
import ssginc_kdt_team3.BE.DTOs.deposit.AdminDepositDTO;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.DepositStatus;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
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

    @Test
    public void MonthlyDeposit() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth())
                .with(LocalTime.MIN);
        LocalDateTime endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth())
                .with(LocalTime.MAX);

        Integer all = repository.findMonthlyAll(1L, startOfMonth , endOfMonth);
        System.out.println(all);
    }

    @Test
    public void MonthlyPenalty() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth())
                .with(LocalTime.MIN);
        LocalDateTime endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth())
                .with(LocalTime.MAX);

        int all = repository.findMonthlyPenalty(1L, startOfMonth , endOfMonth);
        System.out.println(all);
    }

    @Test
    public void MonthlyRefund() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth())
                .with(LocalTime.MIN);
        LocalDateTime endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth())
                .with(LocalTime.MAX);

        Integer all = repository.findMonthlyRefund(1L, DepositStatus.RETURN,startOfMonth , endOfMonth);
        Integer all2 = repository.findMonthlyRefund(1L, DepositStatus.PART_RETURN,startOfMonth , endOfMonth);

        System.out.println(Integer.sum(all,all2));
    }

    @Test
    public void MonthlyPay() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth())
                .with(LocalTime.MIN);
        LocalDateTime endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth())
                .with(LocalTime.MAX);

        Integer all = repository.findMonthlyPayment(1L, DepositStatus.PAYMENT,startOfMonth , endOfMonth);
        System.out.println(all);
    }
}
