package ssginc_kdt_team3.BE.repository.point;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class pointRepositoryTest {

    @Autowired
    JpaDataPointDetailRepository pointDetailRepository;

    @Test
    public void 잔액_조회() {
        List<Object[]> objects = pointDetailRepository.balanceInquiry(1L);
        for (Object[] l : objects) {
            Long s =(Long) l[0];
            Long s1 =(Long) l[1];
            LocalDate localDate = (LocalDate) l[2];

            System.out.println("id = " + s);
            System.out.println("value = " + s1);
            System.out.println("endDate = " + localDate.toString());
        }
    }
}
