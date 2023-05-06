package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerChangePwDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerChangePwService;

@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor

public class OwnerChangePwController {
    @Autowired
    private final OwnerChangePwService changePwSer;

    @PostMapping("/changepassword")
    public ResponseEntity<String> pwCheckController (@RequestBody OwnerChangePwDTO changePwDTO) {
        boolean PwCheck = changePwSer.CheckPw(changePwDTO);
        if (!PwCheck) {
            return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        try {
            changePwSer.ChangePw(changePwDTO);
        }catch (Exception e){
            return new ResponseEntity<>("비밀번호가 서로 다릅니다.",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("비밀번호 변경이 완료되었습니다.",HttpStatus.OK);
    }
}


