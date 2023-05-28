package ssginc_kdt_team3.BE.controller.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerJoinDTO;
import ssginc_kdt_team3.BE.DTOs.kakao.KakaoPayApproveResponseDTO;
import ssginc_kdt_team3.BE.DTOs.kakao.KakaoPayReadyResponseDTO;
import ssginc_kdt_team3.BE.DTOs.kakao.KakaoRefundResponseDTO;
import ssginc_kdt_team3.BE.exception.BusinessLogicException;
import ssginc_kdt_team3.BE.service.customer.CustomerKakaoPayService;
import ssginc_kdt_team3.BE.service.customer.KakaoService;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/charge")
public class CustomerChargeController {

    private final CustomerKakaoPayService kakaoPayService;

    /**
     * 결제요청
     */
    @PostMapping("/ready")
    public ResponseEntity readyToKakaoPay(@RequestBody Map<String, String> item) {

        String name = item.get("name");
        String value = item.get("value");
        String price = item.get("price");
        String customerId = item.get("customerId");

        KakaoPayReadyResponseDTO kakaoPayReadyResponseDTO = kakaoPayService.kakaoReady(name, value, price, customerId);

        if (kakaoPayReadyResponseDTO != null) {
           return ResponseEntity.ok(kakaoPayReadyResponseDTO);
        }

        return ResponseEntity.badRequest().body("존재하지 않는 유저의 Id 입니다.");
    }

    /**
     * 결제 성공
     */
    @GetMapping("/success")
    public ResponseEntity afterPayRequest(@RequestParam("pg_token") String pgToken) {

        KakaoPayApproveResponseDTO kakaoApprove = kakaoPayService.ApproveResponse(pgToken);

        return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
    }

    /**
     * 환불
     */
    @PostMapping("/refund")
    public ResponseEntity refund() {

        KakaoRefundResponseDTO kakaoCancelResponse = kakaoPayService.kakaoCancel();

        return new ResponseEntity<>(kakaoCancelResponse, HttpStatus.OK);
    }

    /**
     * 결제 진행 중 취소
     */
    @GetMapping("/cancel")
    public ResponseEntity<String> cancel() {

        return ResponseEntity.ok("취소가 완료 되었습니다.");
    }

    /**
     * 결제 실패
     */
    @GetMapping("/fail")
    public ResponseEntity<String> fail() {

        return ResponseEntity.badRequest().body("결제에 실패했습니다.");
    }


}