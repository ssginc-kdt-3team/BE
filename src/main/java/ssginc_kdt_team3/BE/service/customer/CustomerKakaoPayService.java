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
import ssginc_kdt_team3.BE.domain.ChargingDetail;
import ssginc_kdt_team3.BE.domain.ChargingManagement;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.PaymentManaging;
import ssginc_kdt_team3.BE.repository.charging.JpaDataChargingDetailRepository;
import ssginc_kdt_team3.BE.repository.charging.JpaDataChargingManagementRepository;
import ssginc_kdt_team3.BE.repository.customer.JpaCustomerRepository;
import ssginc_kdt_team3.BE.repository.customer.JpaDataCustomerRepository;
import ssginc_kdt_team3.BE.repository.payManaging.JpaDataPayManagingRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CustomerKakaoPayService {

    private final JpaDataPayManagingRepository payManagingRepository;
    private final JpaDataCustomerRepository customerRepository;
    private final JpaDataChargingManagementRepository chargingManagementRepository;
    private final JpaDataChargingDetailRepository chargingDetailRepository;

    static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드

    @Value("${kakaoAdmin}")
    private String admin_Key; // 공개 조심! 본인 애플리케이션의 어드민 키를 넣어주세요

    private KakaoPayReadyResponseDTO kakaoReady;
    private Long customerId;

    public KakaoPayReadyResponseDTO kakaoReady(String name, String value, String price, String id) {

        log.info("결제 요청");

        customerId = Long.parseLong(id);

        Optional<Customer> byId = customerRepository.findById(customerId);

        if (byId.isPresent()) {
            // 카카오페이 요청 양식
            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
            parameters.add("cid", cid);
            parameters.add("partner_order_id", "가맹점 주문 번호");
            parameters.add("partner_user_id", id);
            parameters.add("item_name", name);
            parameters.add("quantity", value);
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

        return null;
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
        parameters.add("partner_user_id", Long.toString(customerId));
        parameters.add("pg_token", pgToken);

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoPayApproveResponseDTO approveResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoPayApproveResponseDTO.class);

        long customerId = Long.parseLong(approveResponse.getPartner_user_id());

        Optional<Customer> byId = customerRepository.findById(customerId);
        Customer customer = byId.get();

        //충전 정보 DB 저장
        PaymentManaging paymentManaging = new PaymentManaging(approveResponse);
        PaymentManaging save = payManagingRepository.save(paymentManaging);
        log.info("결제 요청 KakaoPayApproveResponseDTO = {}", approveResponse.toString());
        log.info("충전 tid : {}", paymentManaging.getTid());
        log.info("충전 가격 : {}", paymentManaging.getAmount());
        log.info("충전 날짜 : {}", paymentManaging.getDate());


        ChargingManagement chargingManagement = ChargingManagement.builder().status(true)
                    .changeReason("카카오페이 충전")
                    .value(paymentManaging.getAmount())
                    .changeDate(paymentManaging.getDate())
                    .customer(customer)
                    .deposit(null)
                    .paymentManaging(save).build();

        ChargingDetail chargingDetail = ChargingDetail.builder()
                .value(paymentManaging.getAmount())
                .status(true)
                .operateDate(paymentManaging.getDate())
                .chargingManagement(chargingManagement).build();

        chargingManagementRepository.save(chargingManagement);
        ChargingDetail detail = chargingDetailRepository.save(chargingDetail);
        detail.setDetailUseId();
        chargingDetailRepository.save(detail);

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
