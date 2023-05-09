package ssginc_kdt_team3.BE.controller.admin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerUpdateDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.service.admin.AdminOwnerService;
//import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;
//import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/owner")
public class AdminOwnerController {

//    private final OwnerJoinService joinService;
    private final AdminOwnerService ownerService;

    @PostMapping("/join")
    public ResponseEntity<String> ownerJoin(@RequestBody OwnerJoinDTO ownerJoinDTO) {
        try {
//            joinService.join(ownerJoinDTO);
            return ResponseEntity.ok("회원가입이 완료되었습니다!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<Page<Owner>> findAll() {
        Pageable pageable = PageRequest.of(0, 5);
        ResponseEntity<Page<Owner>> response = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(ownerService.findAllOwner(pageable));

        return response;
    }

    @GetMapping("/findOne/{id}")
    public ResponseEntity<Owner> findOne(@PathVariable(name = "id") Long ownerId) {

        Optional<Owner> one = ownerService.findOne(ownerId);

        if (one.isPresent()) {
            return ResponseEntity.ok(one.get());
        } else {
            return null;
        }
    }

    @PostMapping("/update/{id}")
    public boolean updateOwner(@PathVariable(name = "id") Long ownerId,
                               @RequestBody OwnerUpdateDTO ownerUpdateDTO) {

        return ownerService.updateOwnerInfo(ownerId, ownerUpdateDTO);
    }
}