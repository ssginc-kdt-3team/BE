package ssginc_kdt_team3.BE.service.Owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;
import ssginc_kdt_team3.BE.DTOs.customer.Address;
import javax.sql.DataSource;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class OwnerJoinServiceTest {

    @Autowired
    JpaDataOwnerRepository repo;
    @Autowired
    OwnerJoinService ser;
    @Autowired
    DataSource dataSource;

    private OwnerJoinDTO owners;

    @BeforeEach
    public void clean(){
        repo.deleteAll();
    }

    @Test
    public void joinServiceTest2() throws Exception{

        Address address = new Address("부산시","해운대구","센텀 리더스마크","123-123");
        owners = new OwnerJoinDTO("User22@naver.com","User22","Zxc@987!!","010-9999-8888",LocalDate.parse("1999-05-05"),true,address);

        ser.join(owners);

        String email = owners.getEmail();
        System.out.println("회원가입 결과 : " + repo.findByEmail(email));

        Address address2 = new Address("서울시","서초구","33층","123-123");
        owners = new OwnerJoinDTO("User22@naver.com","가나다","Zxc@987!!","010-3333-3333",LocalDate.parse("1999-05-05"),true,address2);

        try {
            ser.join(owners);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}













