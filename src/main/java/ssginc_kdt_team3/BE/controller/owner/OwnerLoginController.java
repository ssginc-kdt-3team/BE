package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerLoginDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerLoginService;

@RequestMapping("/owner")
@RestController
@RequiredArgsConstructor
@Controller

public class OwnerLoginController {

    @Autowired
    private final OwnerLoginService Service;

//    @GetMapping("/login")
//    public String login(){
//        return "login";
//        //로그인 창을 띄우는 것 까지는 Get방식으로 처리하고
//        //로그인 확인 버튼을 누르는 순간 Post방식으로 처리
//    }

    @PostMapping("/login")
    public ResponseEntity<String> loginCheck(@RequestBody OwnerLoginDTO ownerLogin) {

        try {
            String OwnerName = Service.loginCheck(ownerLogin);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}