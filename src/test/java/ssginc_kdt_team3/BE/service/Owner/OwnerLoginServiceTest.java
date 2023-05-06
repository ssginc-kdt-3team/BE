package ssginc_kdt_team3.BE.service.Owner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerLoginDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;
import ssginc_kdt_team3.BE.service.owner.OwnerLoginService;

import java.time.LocalDate;
import java.util.Optional;

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

    private OwnerJoinDTO JoinDTO;

    @BeforeEach
    public void OwnerDTosSet(){
        JoinDTO = new OwnerJoinDTO();
        LoginDTO = new OwnerLoginDTO();
    }

    @Test
    public void OwnerLoginTest() throws Exception{

        JoinDTO.setName("가나다");
        JoinDTO.setEmail("naver@gmail.com");
        JoinDTO.setPassword("qwerty1234");
        JoinDTO.setPhone("010-1111-2222");
        JoinDTO.setGender(true);
        JoinDTO.setBirthday(LocalDate.parse("2000-01-04"));

        try{
            Join.join(JoinDTO);
        }catch(Exception e){
            throw new Exception();
        }

        Optional<Owner> owner2 = repo.findByEmail(JoinDTO.getEmail());
        System.out.println("JoinTest결과 : " + owner2);

        LoginDTO.setEmail("naver@gmail.com");
        LoginDTO.setPassword("qwerty1234");

        String email = LoginDTO.getEmail();
        String pass = LoginDTO.getPassword();

        Optional<Owner> Info = Repository.findByEmail(LoginDTO.getEmail());
        System.out.println("Repo결과 : " + Info);

        try{
            String name = Login.loginCheck(LoginDTO);
            System.out.println(name + "님, 안녕하세요!");
        }catch (Exception e){
            throw new Exception();
        }
    }
    @AfterEach
    public void clean(){
        repo.deleteAll();
    }

}
