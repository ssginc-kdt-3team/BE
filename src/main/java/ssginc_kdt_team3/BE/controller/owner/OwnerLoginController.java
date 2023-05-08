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
import ssginc_kdt_team3.BE.DTOs.owner.OwnerLoginDTO;
//import ssginc_kdt_team3.BE.service.owner.OwnerLoginService;

@RequestMapping("/owner")
@RestController
@RequiredArgsConstructor
@Controller

public class OwnerLoginController {

//    @Autowired
//    private final OwnerLoginService Service;
//
//    @PostMapping("/login")
//    public ResponseEntity<String> loginCheck(@RequestBody OwnerLoginDTO ownerLogin) {
//
//        try {
//            String OwnerEmail = Service.loginCheck(ownerLogin);
//            return new ResponseEntity<>(OwnerEmail+"님, 환영합니다!", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
}