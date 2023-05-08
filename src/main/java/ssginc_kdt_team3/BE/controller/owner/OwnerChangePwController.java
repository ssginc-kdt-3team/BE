package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.owner.CheckPwDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerChangePwDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerChangePwService;


@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor

public class OwnerChangePwController {
    @Autowired
    private final OwnerChangePwService changePwSer;

//    @PostMapping("/infocheck")
//    public ResponseEntity<String> pwCheckController (@RequestBody CheckPwDTO check) {
//        try {
//            changePwSer.CheckPw(check);
//            return new ResponseEntity<>("인증 성공", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//    @PostMapping("/changepassword")
//    public ResponseEntity<String> pwChangeController(@RequestBody OwnerChangePwDTO changePwDTO){
//        try {
//            changePwSer.ChangePw(changePwDTO);
//            return new ResponseEntity<>("비밀번호 변경이 완료되었습니다.",HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>("비밀번호가 서로 다릅니다.",HttpStatus.BAD_REQUEST);
//        }

//    }
}


