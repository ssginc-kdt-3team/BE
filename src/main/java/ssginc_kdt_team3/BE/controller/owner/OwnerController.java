package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.admin.AdminOwnerDetailDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerLoginDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;
import ssginc_kdt_team3.BE.service.owner.OwnerLoginService;
import ssginc_kdt_team3.BE.service.owner.OwnerViewSelfPrivacyService;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/owner")
@RestController
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerViewSelfPrivacyService ownerViewSelfPrivacyService;

    private final OwnerJoinService ownerJoinService;

    private final OwnerLoginService Service;

    @PostMapping("/join")
    public ResponseEntity<String> joinControl(@RequestBody OwnerJoinDTO ownerJoinDTO) {
        log.info("log info = {}",ownerJoinDTO);
        try{
            ownerJoinService.join(ownerJoinDTO);
            return new ResponseEntity<>("회원가입이 완료되었습니다!",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("회원가입 실패",HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping("/login")
    public ResponseEntity<Map> loginCheck(@RequestBody OwnerLoginDTO ownerLogin) {

        try {
            Map map = new HashMap();
            Long ownerId = Service.loginCheck(ownerLogin);
            map.put("id", ownerId);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return null;
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<AdminOwnerDetailDTO> ownerViewSelfDetailPrivacyController(@PathVariable("id")Long id) throws Exception {
            ResponseEntity<AdminOwnerDetailDTO> successResponse = ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(ownerViewSelfPrivacyService.ownerViewSelfDetail(id));

            return successResponse;
    }
}