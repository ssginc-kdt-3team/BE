package ssginc_kdt_team3.BE.service.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ssginc_kdt_team3.BE.DTOs.kakao.KakaoPayApproveResponseDTO;
import ssginc_kdt_team3.BE.DTOs.kakao.KakaoPayReadyResponseDTO;
import ssginc_kdt_team3.BE.DTOs.kakao.KakaoRefundResponseDTO;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CustomerKakaoPayService {

    static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드

    @Value("${kakaoAdmin}")
    private String admin_Key; // 공개 조심! 본인 애플리케이션의 어드민 키를 넣어주세요

    private KakaoPayReadyResponseDTO kakaoReady;

    public KakaoPayReadyResponseDTO kakaoReady(String name, String price) {

        log.info("결제 요청");

        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", "가맹점 주문 번호");
        parameters.add("partner_user_id", "가맹점 회원 ID");
        parameters.add("item_name", name);
        parameters.add("quantity", "1");
        parameters.add("total_amount", price);
        parameters.add("vat_amount", Integer.toString(Integer.parseInt(price)/10));
        parameters.add("tax_free_amount", "0");
        parameters.add("approval_url", "http://localhost:8080/customer/charge/success"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8080/customer/charge/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8080/customer/charge/fail"); // 실패 시 redirect url

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        kakaoReady = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoPayReadyResponseDTO.class);

        return kakaoReady;
    }

    /**
     * 결제 완료 승인
     */
    public KakaoPayApproveResponseDTO ApproveResponse(String pgToken) {

        log.info("결제 승인");

        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", kakaoReady.getTid());
        parameters.add("partner_order_id", "가맹점 주문 번호");
        parameters.add("partner_user_id", "가맹점 회원 ID");
        parameters.add("pg_token", pgToken);

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoPayApproveResponseDTO approveResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoPayApproveResponseDTO.class);

        log.info("결제 요청 KakaoPayApproveResponseDTO = {}", approveResponse.toString());

        return approveResponse;
    }

    /**
     * 결제 환불
     */
    public KakaoRefundResponseDTO kakaoCancel() {

        // 카카오페이 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", "환불할 결제 고유 번호");
        parameters.add("cancel_amount", "환불 금액");
        parameters.add("cancel_tax_free_amount", "환불 비과세 금액");
        parameters.add("cancel_vat_amount", "환불 부가세");

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoRefundResponseDTO cancelResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/cancel",
                requestEntity,
                KakaoRefundResponseDTO.class);

        return cancelResponse;
    }

    /**
     * 카카오 요구 헤더값
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + admin_Key;

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }
}
