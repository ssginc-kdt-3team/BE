package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerFindPwDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerNewPwDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerFindPwService;
import ssginc_kdt_team3.BE.service.owner.OwnerNewPwService;


//@RestController
//@RequestMapping("/owner")
//@RequiredArgsConstructor

//public class OwnerFindPwController {
//    @Autowired
//    private final OwnerFindPwService ownerFindPwService;
//
//    @Autowired
//    private final OwnerNewPwService newPwService;
//
//    @PostMapping("/findpw")
//    public ResponseEntity<String> OwnerFindPw(@RequestBody OwnerFindPwDTO ownerFindPwDTO) throws Exception{


//        try {
//            ownerFindPwService.findPw(ownerFindPwDTO);
//            return new ResponseEntity<>("이메일, 이름, 전화번호가 일치합니다.",HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }

//    }
//    @PostMapping("/newPassword")
//    public ResponseEntity<String> newPw(@RequestBody OwnerNewPwDTO newPwDTO){
//        try {
//            newPwService.NewPw(newPwDTO);
//        }catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>("비밀번호 변경이 완료되었습니다.",HttpStatus.OK);
//
//    }
//}
