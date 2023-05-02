package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.repository.owner.OwnerJoinRepository;
import ssginc_kdt_team3.BE.service.interfaces.owner.OwnerJoinService;

import java.lang.reflect.Member;
@RequiredArgsConstructor
@Controller
public class OwnerJoinController {
    private final OwnerJoinService ownerJoinService;

    @PostMapping("/ownerjoin")
    public void joinOwner(OwnerJoinDTO ownerJoinDTO){
        OwnerJoinDTO saveOwner = ownerJoinService.join(ownerJoinDTO);
    }

}
