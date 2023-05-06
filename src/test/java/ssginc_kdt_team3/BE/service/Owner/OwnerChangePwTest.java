package ssginc_kdt_team3.BE.service.Owner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerChangePwDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerNewPwDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
import ssginc_kdt_team3.BE.service.owner.OwnerChangePwService;
import ssginc_kdt_team3.BE.service.owner.OwnerNewPwService;

import java.time.LocalDate;
import java.util.Optional;


@SpringBootTest
public class OwnerChangePwTest {
    @Autowired
    private JpaDataOwnerRepository repo;
    @Autowired
    private OwnerChangePwService ser;

    OwnerChangePwDTO pwDTO;
    OwnerJoinDTO JoinDTO;

    @Test
    public void test1() throws Exception{
        JoinDTO = new OwnerJoinDTO();

        JoinDTO.setName("가나다");
        JoinDTO.setEmail("naver@gmail.com");
        JoinDTO.setPassword("qwerty1234");
        JoinDTO.setPhone("010-1111-2222");
        JoinDTO.setGender(true);
        JoinDTO.setBirthday(LocalDate.parse("2000-01-04"));

        System.out.println("JoinDTO SET : " + JoinDTO.toString());
        System.out.println("==============================");
        System.out.println("==============================");

        pwDTO = new OwnerChangePwDTO();

        pwDTO.setEmail("naver@gmail.com");
        pwDTO.setNowPassword("qwerty");
        pwDTO.setNewPassword1("qwertyqwerty");
        pwDTO.setNewPassword2("qwertyqwerty");
        try{
            ser.CheckPw(pwDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
        ser.ChangePw(pwDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("pwDTO SET : " + pwDTO.toString());
        System.out.println("==============================");
        System.out.println("==============================");

        Optional<Owner> test = repo.findByEmail("naver@gmail.com");


    }

}
