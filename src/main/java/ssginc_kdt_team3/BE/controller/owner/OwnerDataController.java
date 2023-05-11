package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.owner.*;
import ssginc_kdt_team3.BE.service.owner.OwnerChangePwService;
import ssginc_kdt_team3.BE.service.owner.OwnerFindPwService;
import ssginc_kdt_team3.BE.service.owner.OwnerUpdateService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/owner/update")


public class OwnerDataController {
    @Autowired
    private final OwnerUpdateService update;
    @Autowired
    private final OwnerFindPwService ser;

    @PostMapping("/info")
    public ResponseEntity<String> updateInfo(@RequestParam("id")long id,@RequestBody OwnerUpdateDTO ownerUpdateDTO) {

        update.OwnerUpdate(id,ownerUpdateDTO);

        return new ResponseEntity<>("정보 수정이 완료되었습니다!", HttpStatus.OK);
        //점주 정보 수정

    }//로그인 되어있다는 가정하에

    @PostMapping("/password")
    public ResponseEntity<String> OwnerFindPw(@RequestBody OwnerFindPwDTO ownerFindPwDTO) throws Exception{

        try {
            ser.findPw(ownerFindPwDTO);
            return new ResponseEntity<>("이메일, 이름, 전화번호가 일치합니다.", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping("/newPassword")
    public ResponseEntity<String> newPw(@RequestBody OwnerNewPwDTO newPwDTO){
        try {
            ser.NewPw(newPwDTO);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("비밀번호 변경이 완료되었습니다.",HttpStatus.OK);

    }

}
