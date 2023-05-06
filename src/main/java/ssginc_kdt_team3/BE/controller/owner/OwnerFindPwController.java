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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor

public class OwnerFindPwController {
    @Autowired
    private final OwnerFindPwService ownerFindPwService;

    @PostMapping("/findpw")
    public ResponseEntity<Map<Object, String>> OwnerFindPw(@RequestBody OwnerFindPwDTO ownerFindPwDTO) throws Exception{


        try {
            ownerFindPwService.findPw(ownerFindPwDTO);
        }catch (Exception e){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

        String TempPw = ownerFindPwService.TemporaryPw();

        Map<Object,String> ResponseBody = new HashMap<>();
        ResponseBody.put("data","임시 비밀번호 발급 완료");
        ResponseBody.put("TempPassword",TempPw);

        return new ResponseEntity<>(ResponseBody,HttpStatus.OK);
    }
}
