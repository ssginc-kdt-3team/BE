package ssginc_kdt_team3.BE.controller.customer;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.customer.*;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Grade;
import ssginc_kdt_team3.BE.exception.ErrorResponse;
import ssginc_kdt_team3.BE.repository.customer.JpaDataCustomerRepository;
import ssginc_kdt_team3.BE.service.customer.CustomerService;
import ssginc_kdt_team3.BE.service.pointManagement.PointManagementService;
import ssginc_kdt_team3.BE.util.TimeUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
  private final CustomerService customerService;
  private final JpaDataCustomerRepository customerRepository;
  private final PointManagementService pointManagementService;

  @GetMapping("Test")
  public boolean test() {
    Optional<Customer> byId = customerRepository.findById(1L);
    Customer customer = byId.get();
    boolean test = pointManagementService.getPointSave(true, 1000, "테스트",customer, TimeUtils.findNow());

    return test;
  }

  // 회원가입
  @PostMapping("/join")
  public ResponseEntity join(@Validated @RequestBody CustomerJoinDTO customerJoinDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
      // 200 response with 404 status code
      return ResponseEntity.ok(new ErrorResponse("404", "Validation failure", errors));
    }
      CustomerJoinDTO joinDto = customerService.join(customerJoinDTO);
      return ResponseEntity.ok(joinDto);
  }

  // 중복이메일 검증
  @PostMapping("/emailCheck")
  public boolean emailCheck(@RequestBody Map<String, String> email) {
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

  // 등급조회
  @PostMapping("/grade/{id}")
  public ResponseEntity<Grade> getGradeInfo(@PathVariable(name = "id") Long customerId){
    Grade grade = customerService.gradeInfo(customerId);
    return ResponseEntity.status(HttpStatus.OK).body(grade);
  }
}

