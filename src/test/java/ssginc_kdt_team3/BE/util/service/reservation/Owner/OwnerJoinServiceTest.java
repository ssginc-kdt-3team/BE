package ssginc_kdt_team3.BE.util.service.reservation.Owner;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;
import ssginc_kdt_team3.BE.DTOs.Address;
import java.time.LocalDate;

@Slf4j
@SpringBootTest
class OwnerJoinServiceTest {

    @Autowired
    OwnerJoinService service;

    OwnerJoinDTO ownerJoinDTO = new OwnerJoinDTO();

    @Test
    public void ownerJoinTest() throws Exception{

        Address address = new Address("부산시","해운대구","센텀 리더스마크","123-123");
        LocalDate birthday = LocalDate.of(2000,5,5);

        ownerJoinDTO.setEmail("testUser@naver.com");
        ownerJoinDTO.setPassword("qwer1234");
        ownerJoinDTO.setName("김가나다");
        ownerJoinDTO.setPhone("01012349999");
        ownerJoinDTO.setGender(false);
        ownerJoinDTO.setAddress(address);
        ownerJoinDTO.setBirthday(birthday);

        try{
            service.join(ownerJoinDTO);
        }catch (Exception e){
            log.info("error! = {}",e.getMessage());
        }

    }
    @Test
    public void ownerJoinTestIfExistsByEmail() throws Exception {
        Address address2 = new Address("서울시","서초구","어딘가 건물 3층","123-123");
        OwnerJoinDTO ownerJoinDTO2 = new OwnerJoinDTO("testUser@naver.com","가나다","qwer1234","010-3333-3333",LocalDate.parse("1990-05-05"),true,address2);

        try{
            service.join(ownerJoinDTO2);
        }catch (Exception e){
            log.info("error! = {}",e.getMessage());
        }
    }
}













