package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerFindPwDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerFindPwService;

@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor

public class OwnerFindPwController {
    @Autowired
    private final OwnerFindPwService ownerFindPwService;

    @PostMapping("/findpw")
    public ResponseEntity<String> OwnerFindPw(@RequestBody OwnerFindPwDTO ownerFindPwDTO) throws Exception{

        ownerFindPwService.findPw(ownerFindPwDTO);

        String TempPw = ownerFindPwService.TemporaryPw();
        HttpHeaders headers = new HttpHeaders();
        headers.set("임시 비밀번호",TempPw);

        return new ResponseEntity<>("임시 비밀번호 발급",headers,HttpStatus.OK);
    }
}
