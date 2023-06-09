package ssginc_kdt_team3.BE.util.service.reservation.Owner;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerUpdateDTO;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
import ssginc_kdt_team3.BE.service.admin.AdminOwnerService;

@Slf4j
@SpringBootTest
public class OwnerUpdateTest {

    @Autowired
    AdminOwnerService adminOwnerService;

    OwnerUpdateDTO updateDTO = new OwnerUpdateDTO();

    @Test
    public void OwnerUpdate(){

        Long ownerId = 53L;

        updateDTO.setPhone("01012123434");
        updateDTO.setName("임태경");
        updateDTO.setCity("부산시");
        updateDTO.setDistrict("해운대구");
        updateDTO.setDetail("센텀 리더스마크");
        updateDTO.setZipCode("123-456");

        try {
            adminOwnerService.updateOwnerInfo(ownerId,updateDTO);
        }catch (Exception e){
            log.info("error! = {}",e.getMessage());
        }
    }
}
