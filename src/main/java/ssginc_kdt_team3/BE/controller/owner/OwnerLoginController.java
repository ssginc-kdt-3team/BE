package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerLoginDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.service.owner.OwnerLoginService;

@RequestMapping("/owner")
@RestController
@RequiredArgsConstructor
@Controller

public class OwnerLoginController {
    @Autowired
    private final OwnerLoginService Service;

    @GetMapping("/login")
    public ResponseEntity<String> loginCheck(@RequestBody OwnerLoginDTO ownerLogin) {

        try {
            String OwnerName = Service.loginCheck(ownerLogin);
            return ResponseEntity.status(HttpStatus.OK).body(OwnerName);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
}