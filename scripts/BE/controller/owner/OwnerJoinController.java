package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;


@RequestMapping("/owner")
@RestController
@RequiredArgsConstructor
@Controller

public class OwnerJoinController {
    @Autowired
    private final OwnerJoinService ownerJoinService;

    @PostMapping("/join")
    public ResponseEntity<String> joinControl(@RequestBody OwnerJoinDTO ownerJoinDTO) {

        try{
            ownerJoinService.join(ownerJoinDTO);
            return new ResponseEntity<>("회원가입이 완료되었습니다!",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("회원가입 실패",HttpStatus.BAD_REQUEST);
        }

    }
}