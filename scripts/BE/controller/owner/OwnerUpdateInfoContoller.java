package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerUpdateDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerUpdateService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/owner")


public class OwnerUpdateInfoContoller {
    @Autowired
    private final OwnerUpdateService update;

    @PostMapping("/updateinfo")
    public ResponseEntity<String> updateInfo(@RequestBody OwnerUpdateDTO ownerUpdateDTO){

        update.OwnerUpdate(ownerUpdateDTO);

        return new ResponseEntity<>("정보 수정이 완료되었습니다!",HttpStatus.OK);

    }

}
