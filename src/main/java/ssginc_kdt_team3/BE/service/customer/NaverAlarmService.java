package ssginc_kdt_team3.BE.service.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ssginc_kdt_team3.BE.DTOs.reservation.Alarm.MessageDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.Alarm.RequestSmsDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.Alarm.ResponseSmsDTO;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NaverAlarmService {
    @Value("${naver-cloud-sms.accessKey}")
    private String accessKey;
    @Value("${naver-cloud-sms.serviceId}")
    private String serviceId;
    @Value("${naver-cloud-sms.secretKey}")
    private String secretKey;
    @Value("${naver-cloud-sms.senderPhone}")
    private String senderPhone;

    public String makeSignature(Long time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/"+ this.serviceId+"/messages";
        String timestamp = time.toString();
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }//SecretKey값을 makeSignature()를 통해 암호화함(time을 파라미터로 넘김)

    public ResponseSmsDTO sendSms(MessageDTO requestContent) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException, URISyntaxException {

        List<MessageDTO> message = new ArrayList<>();
        message.add(requestContent);

        Long time = System.currentTimeMillis();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", makeSignature(time));
        //헤더 생성(타입, 시간, accessKey,암호화된 secretKey등등 설정)

        String alarmType;

        if (requestContent.getContent().length() > 80){
            alarmType = "LMS";
        }
        else{
            alarmType = "SMS";
        }
        RequestSmsDTO request = RequestSmsDTO.builder()
                .type(alarmType)
                .contentType("COMM")//COMM / AD 2가지 옵션있음(AD는 광고용)
                .countryCode("82")//국가 코드
                .from(senderPhone) //발신자 번호
                .content("Test")
                .messages(message)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(request);
        //request객체를 문자열(JSON)로 변환하여 body에 저장함
        HttpEntity<String> httpBody = new HttpEntity<>(body,headers);

        System.out.println(httpBody);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        ResponseSmsDTO response = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/messages"), httpBody, ResponseSmsDTO.class);
        //첫번째 매개변수 = 설정한 URI로 요청을 보냄,두번째 매개변수 = 요청의 Body를 지정
        //세번째 매개변수 = 응답을 받을 형식
        return response;
    }

}