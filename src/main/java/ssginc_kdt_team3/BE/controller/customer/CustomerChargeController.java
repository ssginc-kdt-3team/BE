package ssginc_kdt_team3.BE.controller.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.reservation.charging.CustomerChargeDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.charging.CustomerChargingListDTO;
import ssginc_kdt_team3.BE.DTOs.kakao.KakaoPayApproveResponseDTO;
import ssginc_kdt_team3.BE.DTOs.kakao.KakaoPayReadyResponseDTO;
import ssginc_kdt_team3.BE.DTOs.kakao.KakaoRefundFailResponseDTO;
import ssginc_kdt_team3.BE.DTOs.kakao.KakaoRefundResponseDTO;
import ssginc_kdt_team3.BE.service.customer.CustomerChargingService;
import ssginc_kdt_team3.BE.service.customer.CustomerKakaoPayService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/customer/charge")
public class CustomerChargeController {

    @Value("${admin.pageSize}")
    private int pageSize;

    @Value("${localAddress}")
    private String local;

    @Value("${distributionAddress}")
    private String distribution;

    private final CustomerKakaoPayService kakaoPayService;
    private final CustomerChargingService customerChargingService;

    /**
     * 결제요청
     */
    @ResponseBody
    @PostMapping("/ready")
    public ResponseEntity readyToKakaoPay(@RequestBody CustomerChargeDTO customerChargeDTO) {

        String name = customerChargeDTO.getName();
        String value = customerChargeDTO.getValue();
        String price = customerChargeDTO.getPrice();
        String customerId = customerChargeDTO.getCustomerId();

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
    public String afterPayRequest(@RequestParam("pg_token") String pgToken) {

        KakaoPayApproveResponseDTO kakaoApprove = kakaoPayService.ApproveResponse(pgToken);

        return "redirect:" + distribution + "chargeResult";
    }

    /**
     * 환불
     */
    @ResponseBody
    @PostMapping("/refund/{id}")
    public ResponseEntity refund(@PathVariable(name = "id") Long chargeId) {

        Object kakaoCancelResponse = kakaoPayService.kakaoCancel(chargeId);

        if (kakaoCancelResponse instanceof KakaoRefundFailResponseDTO) {
            return ResponseEntity.badRequest().body(((KakaoRefundFailResponseDTO) kakaoCancelResponse).getFailReason());
        }


        return new ResponseEntity<>((KakaoRefundResponseDTO)kakaoCancelResponse, HttpStatus.OK);
    }

    /**
     * 결제 진행 중 취소
     */
    @GetMapping("/cancel")
    public String cancel() {

        return "redirect:" + distribution + " chargeResult";
    }

    /**
     * 결제 실패
     */
    @GetMapping("/fail")
    public String fail() {

        return "redirect:" + distribution + "chargeResult";
    }

    @ResponseBody
    @GetMapping("/list/{id}/{type}/{date}/{page}")
    public ResponseEntity showChargeList(@PathVariable(name = "id") Long customerId, @PathVariable(name = "type") String type,
                                         @PathVariable(name = "date") int dateType, @PathVariable(name = "page") int page) {
        Map<String, String> result = new HashMap<>();

        ArrayList<String> typeList = new ArrayList<>(Arrays.asList("all", "get", "lost"));
        ArrayList<Integer> dateList = new ArrayList<>(Arrays.asList(12, 1, 3, 6));
        if (typeList.contains(type) && dateList.contains(dateType)) {
            Pageable pageable = PageRequest.of(page-1, pageSize);

            if (type.equals("get")) {
                Page<CustomerChargingListDTO> customerChargingListDTOS = customerChargingService.showCustomerChargingList(customerId, pageable, true, dateType);
                return getResponseEntity(result, customerChargingListDTOS);
            } else if (type.equals("lost")) {
                Page<CustomerChargingListDTO> customerChargingListDTOS = customerChargingService.showCustomerChargingList(customerId, pageable, false, dateType);
                return getResponseEntity(result, customerChargingListDTOS);
            } else {
                Page<CustomerChargingListDTO> customerChargingListDTOS = customerChargingService.showCustomerChargingAndUsingList(customerId, pageable, dateType);
                return getResponseEntity(result, customerChargingListDTOS);
            }
        }

        result.put("error", "존재하지 않는 조회 타입입니다.");
        return ResponseEntity.badRequest().body(result);
    }

    @ResponseBody
    @GetMapping("/check/{id}")
    public ResponseEntity<Map<String, String>> showHoldingAmount(@PathVariable(name = "id") Long customerId) {
        int sum = customerChargingService.showCustomerChargingValue(customerId);
        Map<String, String> result = new HashMap<>();

        if (sum < 0) {
            result.put("error", "존재하지 않는 사용자입니다.");
            return ResponseEntity.badRequest().body(result);
        }

        result.put("value", Integer.toString(sum));

        return ResponseEntity.ok().body(result);
    }

    private ResponseEntity<?> getResponseEntity(Map<String, String> result, Page<CustomerChargingListDTO> customerChargingListDTOS) {
        if (customerChargingListDTOS != null) {
            return ResponseEntity.ok(customerChargingListDTOS);
        } else {
            result.put("error", "존재하지 않는 사용자입니다.");
            return ResponseEntity.badRequest().body(result);
        }
    }
}