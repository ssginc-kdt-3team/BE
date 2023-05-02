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
    public void loginControl(@RequestBody OwnerLoginDTO ownerLoginDTO) {
        Service.loginCheck(ownerLoginDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<Void> loginCheck(@RequestBody OwnerLoginDTO ownerLoginDTO){
        Owner owner = Service.loginCheck(ownerLoginDTO);
        if(owner != null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}