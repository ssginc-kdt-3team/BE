package ssginc_kdt_team3.BE.util.service.reservation.Owner;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.owner.CheckPwDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerChangePwService;

@Slf4j
@SpringBootTest
public class OwnerChangePwTest {

    @Autowired
    OwnerChangePwService Service;

    CheckPwDTO checkDTO = new CheckPwDTO();

    @Test
    public void test1() {

        checkDTO.setEmail("testUser@naver.com");
        checkDTO.setName("김가나다");
        checkDTO.setPassword("qwer1234");
        checkDTO.setPhone("01012349999");

        try {
            Service.CheckPw(checkDTO);
            log.info("success! = {}", "인증완료");
        } catch (Exception e) {
            log.info("error! = {}", e.getMessage());
        }
        //비밀번호 변경 테스트
        //↓ 이메일을 다르게 입력한 경우
        checkDTO.setEmail("zxcUser@naver.com");

        try {
            Service.CheckPw(checkDTO);
            log.info("success! = {}", "인증완료");
        } catch (Exception e) {
            log.info("error! = {}", e.getMessage());
        }
        //비밀번호 변경 테스트
        //↓ 비밀번호를 틀린경우
        checkDTO.setEmail("testUser@naver.com");
        checkDTO.setName("???");

        try {
            Service.CheckPw(checkDTO);
            log.info("success! = {}", "인증완료");
        } catch (Exception e) {
            log.info("error! = {}", e.getMessage());
        }
    }


}
