package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;

@RequiredArgsConstructor
@Controller
public class OwnerController {
    private OwnerJoinService ownerJoinService;

    @PostMapping("/ownerjoin")
    public String joinOwner(@ModelAttribute OwnerJoinDTO ownerJoinDTO) {
        OwnerJoinService.join(ownerJoinDTO);
        return "redirect:/";
    }
}