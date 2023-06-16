package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.owner.*;
import ssginc_kdt_team3.BE.service.admin.AdminOwnerService;
import ssginc_kdt_team3.BE.service.owner.OwnerChangePwService;
import ssginc_kdt_team3.BE.service.owner.OwnerFindPwService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/owner/update")


public class OwnerDataController {
    @Autowired
    private final OwnerChangePwService changePwSer;
    @Autowired
    private final AdminOwnerService update;
    @Autowired
    private final OwnerFindPwService ser;

    @PostMapping("/info/{id}")
    public ResponseEntity<String> updateInfo(@PathVariable("id")Long id,@RequestBody OwnerUpdateDTO ownerUpdateDTO) {
        try {
            update.updateOwnerInfo(id, ownerUpdateDTO);
            return new ResponseEntity<>("업데이트가 성공적으로 처리되었습니다.",HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        //점주 정보 수정
    }
    @PostMapping("/findPassword")
    public ResponseEntity<String> OwnerFindPw(@RequestBody OwnerFindPwDTO ownerFindPwDTO) throws Exception{

        try {
            ser.findPw(ownerFindPwDTO);
            return new ResponseEntity<>("이메일, 이름, 전화번호가 일치합니다.", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }//비밀번호 찾기 - 이메일 , 이름 , 전화번호 인증
        //비밀번호 찾기 & 비밀번호 변경에서 인증성공시 -> 새로운 비밀번호변경 창 표시
    }

    @PostMapping("/newPassword/{id}")
    public ResponseEntity<String> newPw(@RequestBody OwnerNewPwDTO newPwDTO,@PathVariable("id") Long ownerId){
        try {
            ser.NewPw(newPwDTO,ownerId);
            return new ResponseEntity<>("비밀번호 변경이 완료되었습니다.",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/passwordCheck")
    public ResponseEntity<String> pwCheckController (@RequestBody CheckPwDTO check) {
        try {
            changePwSer.CheckPw(check);
            return new ResponseEntity<>("인증 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }//비밀번호 "변경"에서 인증 성공시
    }

}
