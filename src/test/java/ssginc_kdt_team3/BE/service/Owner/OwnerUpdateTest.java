package ssginc_kdt_team3.BE.service.Owner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.cust.Address;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerUpdateDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
import ssginc_kdt_team3.BE.service.owner.OwnerUpdateService;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
public class OwnerUpdateTest {
    @Autowired
    JpaDataOwnerRepository repo;
    @Autowired
    OwnerUpdateService updateService;

    @Test
    public void OwnerUpdate(){

        OwnerJoinDTO JoinDTO = new OwnerJoinDTO();

        JoinDTO.setName("가나다");
        JoinDTO.setEmail("naver@gmail.com");
        JoinDTO.setPassword("qwerty1234");
        JoinDTO.setPhone("010-1111-2222");
        JoinDTO.setGender(true);
        JoinDTO.setBirthday(LocalDate.parse("2000-01-04"));

        Address address0 = new Address("부산시","사상구","5층","112-222");
        JoinDTO.setAdddress(address0);

        System.out.println("업데이트 전 : " + JoinDTO);

        OwnerUpdateDTO UpdateDTO = new OwnerUpdateDTO();

        UpdateDTO.setPhone("010-0000-0000");

        Address address = new Address("부산시","해운대구","센텀 리더스마크 4층","111-111");
        UpdateDTO.setAdddress(address);

        UpdateDTO.setUserStatus(UserStatus.ACTIVE);
        updateService.OwnerUpdate(UpdateDTO);



    }
}
