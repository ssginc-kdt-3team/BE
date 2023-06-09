package ssginc_kdt_team3.BE.util.service.reservation.Owner;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerFindPwDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerNewPwDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerFindPwService;

@Slf4j
@SpringBootTest
public class OwnerFindPwTest {

    @Autowired
    OwnerFindPwService FindPwService;

    OwnerFindPwDTO FindPwDTO = new OwnerFindPwDTO();
    OwnerNewPwDTO newPwDTO = new OwnerNewPwDTO();

    @Test
    void checkOwnerInfo() {

        FindPwDTO.setEmail("testUser@naver.com");
        FindPwDTO.setName("김가나다");
        FindPwDTO.setPhone("01012349999");

        try {
            FindPwService.findPw(FindPwDTO);
            log.info("success! = {}","인증 완료");
        } catch (Exception e) {
            log.info("error! = {}",e.getMessage());
        }
    }
    @Test
    void newPassword(){

        newPwDTO.setNewPassword1("asdf1234");
        newPwDTO.setNewPassword2("asdf1234");
        Long ownerId = 9L;
        try {
            FindPwService.NewPw(newPwDTO,ownerId);
        }catch (Exception e){
            log.info("error! = {}",e.getMessage());

        }
    }
}


