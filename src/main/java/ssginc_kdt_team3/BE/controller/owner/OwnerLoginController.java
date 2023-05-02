package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerLoginDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;
import ssginc_kdt_team3.BE.service.owner.OwnerLoginService;

@RequestMapping("/owner")
@RestController
@RequiredArgsConstructor
@Controller

public class OwnerLoginController {
    private final OwnerLoginService Service;

    @PostMapping("/login")
    public void loginControl(@RequestBody OwnerLoginDTO ownerLoginDTO) {

        Service.loginCheck(ownerLoginDTO);
    }
}