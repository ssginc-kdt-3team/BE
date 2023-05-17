package ssginc_kdt_team3.BE.service.cust;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.repository.branch.BranchRepository;
import ssginc_kdt_team3.BE.service.branch.BranchShopListService;

@SpringBootTest
public class BranchShopTest {

    @Autowired
    BranchRepository repo;

    @Autowired
    private final BranchShopListService ser = new BranchShopListService(repo);

    @Test
    public void Test()throws Exception{
        int num = 2;
        long num2 = num;

        String tt = ser.BranchShop(num2);
        System.out.println(tt);
    }
}
