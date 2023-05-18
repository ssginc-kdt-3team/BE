package ssginc_kdt_team3.BE.controller.customer;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerJoinDTO;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerLoginDTO;
import ssginc_kdt_team3.BE.service.customer.CustomerService;
import ssginc_kdt_team3.BE.service.customer.KakaoService;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
  private final CustomerService customerService;
  private final KakaoService kakaoService;

  //회원가입
  @PostMapping("/join")
  public ResponseEntity<CustomerJoinDTO> join(@RequestBody CustomerJoinDTO customerJoinDTO){

    CustomerJoinDTO joinDto = customerService.join(customerJoinDTO);
    if(joinDto == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return ResponseEntity.status(HttpStatus.OK).body(joinDto);
  }

  /**
  5.4 회원가입 -> 중복이메일 체크 서비스에서 가져와 추가하기
   서비스 코드의 중복검증은 백엔드용, 여기 작업하는건 프론트에서도 검증해야 하니까?
   고객이 아이디 중복버튼을 클릭햇을 때 호출되는 API, 이게 없으면 회원가입 누른 후에야 중복이메일이란 걸 알게 됨.
  */



  @PostMapping("/login")
  public ResponseEntity<Map> login(@RequestBody CustomerLoginDTO customerLoginDTO) {
    log.info("email = {}", customerLoginDTO.getEmail());
    Map loginUser = customerService.login(customerLoginDTO);

    if (loginUser == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return ResponseEntity.status(HttpStatus.OK).body(loginUser);
  }

  @GetMapping("/kakao")
  public void kakao(String code) {
    System.out.println("인가코드: "+code);
    try {
      String access_token = kakaoService.getToken(code);
      System.out.println("토큰:" + access_token);

      // 사용자정보 가져오기
      Map<String, Object> userInfo = kakaoService.getUserInfo(access_token);
      System.out.println("userInfo:" + userInfo);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}

