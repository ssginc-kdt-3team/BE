package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.admin.AdminOwnerDetailDTO;
import ssginc_kdt_team3.BE.DTOs.deposit.OwnerMainDepositDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerLoginDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.service.owner.*;

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

    private final OwnerDepositService depositService;

    private final OwnerReservationService reservationService;

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
    public ResponseEntity<Map<String,Object>> loginCheck(@RequestBody OwnerLoginDTO ownerLogin) {
        try {
            return new ResponseEntity<>(Service.loginCheck(ownerLogin),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<AdminOwnerDetailDTO> ownerViewSelfDetailPrivacyController(@PathVariable("id")Long id) throws Exception {
            ResponseEntity<AdminOwnerDetailDTO> successResponse = ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(ownerViewSelfPrivacyService.ownerViewSelfDetail(id));

            return successResponse;
    }

    @GetMapping("/main/reservation/{id}")
    public ResponseEntity ownerMainMonthReservation(@PathVariable("id") Long id) {
        if (reservationService.showMainMonthlyReservation(id) != null) {
            return ResponseEntity.ok(reservationService.showMainMonthlyReservation(id));
        }

        return ResponseEntity.badRequest().body("id에 해당하는 점주의 매장이 존재하지 않습니다.");
    }

    @GetMapping("/main/deposit/{id}")
    public ResponseEntity ownerMainDeposit(@PathVariable("id") Long id) {
        if (depositService.showMonthlyDeposit(id) != null) {
            return ResponseEntity.ok(depositService.showMonthlyDeposit(id));
        }

        return ResponseEntity.badRequest().body("id에 해당하는 점주의 매장이 존재하지 않습니다.");
    }

    @GetMapping("/main/today/{id}")
    public ResponseEntity ownerMainTodayReservation(@PathVariable("id") Long id) {
        if (reservationService.showMainDailyReservationCnt(id) != null) {
            return ResponseEntity.ok(reservationService.showMainDailyReservationCnt(id));
        }

        return ResponseEntity.badRequest().body("id에 해당하는 점주의 매장이 존재하지 않습니다.");
    }
}