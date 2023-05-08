package ssginc_kdt_team3.BE.service.Owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.customer.Address;

import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerLoginDTO;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;
import ssginc_kdt_team3.BE.service.owner.OwnerLoginService;


import java.time.LocalDate;

@SpringBootTest
public class OwnerLoginServiceTest {

    @Autowired
    JpaDataOwnerRepository repo;
    @Autowired
    OwnerLoginService Login;
    @Autowired
    OwnerJoinService Join;

    @Autowired
    JpaDataOwnerRepository Repository;

    private OwnerLoginDTO LoginDTO;

    private OwnerJoinDTO owners;

    @BeforeEach
    public void 회원세팅 () throws Exception{
        repo.deleteAll();

        Address address = new Address("부산시","해운대구","센텀 리더스마크","123-123");
        owners = new OwnerJoinDTO("User22@naver.com","User22","Zxc@987!!",
                "010-9999-8888",LocalDate.parse("1999-05-05"),
                true,address);
        Join.join(owners);
        System.out.println("완료");
    }
    @Test
    public void OwnerLoginTest() throws Exception{
        LoginDTO = new OwnerLoginDTO("kakao@gmail.com","Zxc@987!!");

        try{
            String email = Login.loginCheck(LoginDTO);
            System.out.println(email + "님, 안녕하세요!");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        LoginDTO.setEmail("User22@naver.com");
        LoginDTO.setPassword("999999999999");

        try{
            String email = Login.loginCheck(LoginDTO);
            System.out.println(email + "님, 안녕하세요!");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        LoginDTO.setPassword("Zxc@987!!");

        try{
            String email = Login.loginCheck(LoginDTO);
            System.out.println(email + "님, 안녕하세요!");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
