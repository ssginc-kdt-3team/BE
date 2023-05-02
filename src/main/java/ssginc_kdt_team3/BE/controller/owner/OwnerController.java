package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RequestMapping("/owner")
@RestController
@RequiredArgsConstructor
@Controller

public class OwnerController {
    private final OwnerJoinService ownerJoinService;

    @PostMapping("/join")
    public void joinControl(@RequestBody OwnerJoinDTO ownerJoinDTO) {

        ownerJoinService.join(ownerJoinDTO);

    }
}