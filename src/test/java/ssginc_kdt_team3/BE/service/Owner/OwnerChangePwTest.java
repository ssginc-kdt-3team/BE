package ssginc_kdt_team3.BE.service.Owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.owner.CheckPwDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerChangePwDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
import ssginc_kdt_team3.BE.service.owner.OwnerChangePwService;
import ssginc_kdt_team3.BE.DTOs.customer.Address;

@SpringBootTest
public class OwnerChangePwTest {
    @Autowired
    private JpaDataOwnerRepository repo;
    @Autowired
    private OwnerChangePwService ser;

    OwnerChangePwDTO pwDTO;

    CheckPwDTO check;

    Owner owner;

    OwnerJoinDTO owners;

    @BeforeEach
    public void Clean(){

        repo.deleteAll();

        owner = new Owner();

        Address address = new Address("부산시","해운대구","센텀 리더스마크","123-123");

        owner.setEmail("?????@gmail.com");
        owner.setName("가나다");
        owner.setPassword("123456789");
        owner.setPhone("010-1234-9998");
        owner.setAddress(address);
        owner.setGender(false);
        owner.setStatus(UserStatus.ACTIVE);

        repo.save(owner);
    }
    @Test
    public void test1() throws Exception{

        check = new CheckPwDTO("vvvvvvvv@naver.com","가나다","123456789");

        try{
            ser.CheckPw(check);
            System.out.println("인증완료");
        }catch (Exception e){
            System.out.println("존재하지 않는 회원입니다!");
        }

        check.setEmail("?????@gmail.com");
        check.setPassword("987654321");

        try{
            ser.CheckPw(check);
            System.out.println("인증완료");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        check.setName("?????");
        check.setPassword("123456789");

        try{
            ser.CheckPw(check);
            System.out.println("인증완료");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        check.setName("가나다");

        try{
            ser.CheckPw(check);
            System.out.println("인증완료");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    @Test
    public void 비밀번호체크(){

        pwDTO = new OwnerChangePwDTO("qwertyqwerty","11111111111111");
        try{
            ser.ChangePw(pwDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        pwDTO.setNewPassword1("11111111111111");
        try{
            ser.ChangePw(pwDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        String ChangePw = repo.PasswordMatchEmail("?????@gmail.com");
        System.out.println("변경후 비밀번호 : " + ChangePw);
    }

}
