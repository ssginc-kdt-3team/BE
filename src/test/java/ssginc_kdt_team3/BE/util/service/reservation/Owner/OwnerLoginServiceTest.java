package ssginc_kdt_team3.BE.util.service.reservation.Owner;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerLoginDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerLoginService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
public class OwnerLoginServiceTest {

    @Autowired
    OwnerLoginService loginService;

    OwnerLoginDTO loginDTO = new OwnerLoginDTO();
    Map<String,Object> hashMap = new HashMap<>();

    @Test
    void OwnerLoginTest(){

        loginDTO.setEmail("testUser@naver.com");
        loginDTO.setPassword("qwer1234");
        try {
        hashMap = loginService.loginCheck(loginDTO);
            log.info("success = {}",hashMap.get("name") + " 님 환영합니다!");
        }catch (Exception e){
            log.info("error! = {}",e.getMessage());
        }
    }
    @Test
    void noExistsEmail(){//존재하지 않는 이메일로 로그인 시도
        loginDTO.setEmail("qwe@naver.com");
        loginDTO.setPassword("qwer1234");

        try {
            hashMap = loginService.loginCheck(loginDTO);
            log.info("success = {}",hashMap.get("name") + " 님 환영합니다!");
        }catch (Exception e){
            log.info("error! = {}",e.getMessage());
        }

    }
    @Test
    void noMatchPassword(){//일치하지 않는 비밀번호 사용
        loginDTO.setEmail("testUser@naver.com");
        loginDTO.setPassword("asdf1234");

        try {
            hashMap = loginService.loginCheck(loginDTO);
            log.info("success = {}",hashMap.get("name") + " 님 환영합니다!");
        }catch (Exception e){
            log.info("error! = {}",e.getMessage());
        }
    }

}
