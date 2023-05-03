package ssginc_kdt_team3.BE.service.Owner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.standard.expression.Each;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;

@SpringBootTest
class OwnerJoinServiceTest {

    @Autowired
    JpaDataOwnerRepository repo;
    OwnerJoinService ser;



    @Test
    public void join(){
        Owner owner1 = new Owner();

        owner1.setEmail("www@naver.com");
        owner1.setPassword("Qwe@123!!");
        owner1.setName("User1");
        owner1.setPhone("010-1234-4567");
        owner1.setRole(UserRole.OWNER);
        owner1.setGender(true);
        owner1.setStatus(UserStatus.ACTIVE);



        repo.save(owner1);


    }

    @AfterEach
    public void clean(){

        repo.deleteAll();
    }

}
