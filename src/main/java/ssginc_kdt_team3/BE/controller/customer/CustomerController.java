package ssginc_kdt_team3.BE.controller.customer;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerFindDTO;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerJoinDTO;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerLoginDTO;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerUpdateDTO;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Grade;
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

  // 회원가입
  @PostMapping("/join")
  public ResponseEntity<CustomerJoinDTO> join(@RequestBody CustomerJoinDTO customerJoinDTO){
    CustomerJoinDTO joinDto = customerService.join(customerJoinDTO);
    if(joinDto == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return ResponseEntity.status(HttpStatus.OK).body(joinDto);
  }

  // 중복이메일 검증
  @PostMapping("/emailCheck")
  public boolean emailCheck(@RequestBody Map<String, String> email){
    String email1 = email.get("email");
    return customerService.validateDuplicateCustomer(email1);
  }

  // 로그인
  @PostMapping("/login")
  public ResponseEntity<Map> login(@RequestBody CustomerLoginDTO customerLoginDTO) {
    log.info("email = {}", customerLoginDTO.getEmail());
    Map loginUser = customerService.login(customerLoginDTO);

    if (loginUser == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return ResponseEntity.status(HttpStatus.OK).body(loginUser);
  }

  // 이메일 찾기
  @PostMapping("/findEmail")
  public ResponseEntity<String> findEmail(@RequestBody CustomerFindDTO findDTO){
    Customer findInfo = customerService.getCustomerEmail(findDTO);

    if (findInfo == null) { // 이거 서비스코드에서 NoSuchElementException 했는데, 여기서도 if문 처리해야 하나?
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    String email = findInfo.getEmail();
    return ResponseEntity.status(HttpStatus.OK).body(email);
  }

  // 비밀번호 찾기
  @PostMapping("/findPwd")
  public ResponseEntity<String> findPwd(@RequestBody CustomerFindDTO findDTO){

    Customer findInfo = customerService.getCustomerPassword(findDTO);

    if (findInfo == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    String password = findInfo.getPassword();
    return ResponseEntity.status(HttpStatus.OK).body(password);
  }

  // 개인정보 변경
//  @PostMapping("/updateInfo/{id}")
//  public boolean CustomerUpdate(@PathVariable(name = "id") Long CustomerId,
//                                @RequestBody CustomerUpdateDTO updateDTO) {
//    return ;
//  }
//
//
//  // 비밀번호 변경
//  @PostMapping("/updatePwd/{id}")
//  public boolean CustomerUpdate(@PathVariable(name = "id") Long CustomerId,
//                                @RequestBody CustomerUpdateDTO updateDTO) {
//    return ;
//  }


  // 카카오 로그인: 미완성
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

  // 등급조회
  @PostMapping("/grade/{id}")
  public ResponseEntity<Grade> getGradeInfo(@PathVariable(name = "id") Long customerId){
    Grade grade = customerService.gradeInfo(customerId);
    return ResponseEntity.status(HttpStatus.OK).body(grade);
  }



}

