package ssginc_kdt_team3.BE.controller.customer;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.customer.*;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Grade;
import ssginc_kdt_team3.BE.service.customer.CustomerService;
import ssginc_kdt_team3.BE.service.customer.KakaoService;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.status(HttpStatus.OK).body(loginUser);
  }

  // 이메일 찾기
  @PostMapping("/findEmail")
  public ResponseEntity<String> findEmail(@RequestBody EmailFindDTO emailFindDTO){
    EmailFindDTO findDTO = new EmailFindDTO();
    findDTO.setName(emailFindDTO.getName());
    findDTO.setPhone(emailFindDTO.getPhone());

    // 서비스 코드에서 옵셔널로 커스토머 자체를 던지고, 컨트롤러에서 값이 있으면 성공, 없으면 에러로 분기처리

    Optional<Customer> findCustomer = customerService.getCustomerEmail(findDTO.getName(), findDTO.getPhone());

    if (findCustomer.isPresent()) {
      return ResponseEntity.status(HttpStatus.OK).body(findCustomer.get().getEmail());
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 정보가 없습니다.");
  }

  // 비밀번호 찾기
  @PostMapping("/findPwd")
  public ResponseEntity<String> findPwd(@RequestBody PasswordFindDTO passwordDTO){

    PasswordFindDTO findDTO = new PasswordFindDTO();
    findDTO.setName(passwordDTO.getName());
    findDTO.setEmail(passwordDTO.getEmail());
    findDTO.setPhone(passwordDTO.getPhone());

    Optional<Customer> findPassword = customerService.getCustomerPassword(findDTO);

    if (findPassword.isPresent()) {
      return ResponseEntity.status(HttpStatus.OK).body(findPassword.get().getPassword());
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 정보가 없습니다.");
  }

  // 개인정보 변경
//  @PostMapping("/updateInfo/{id}")
//  public void updateInfo(@PathVariable(name = "id") Long customerId,
//                             @RequestBody CustomerUpdateDTO updateDTO) {
//    customerService.updateInfo(updateDTO, customerId);
//  }

  // 비밀번호 변경
//  @PostMapping("/updatePwd/{id}")
//  public void updatePassword(@PathVariable(name = "id") Long customerId,
//                                @RequestBody PwdUpdateDTO updateDTO) {
//    customerService.updatePassword(updateDTO, customerId);
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

