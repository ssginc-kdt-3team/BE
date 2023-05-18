package ssginc_kdt_team3.BE.service.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.customer.*;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.customer.JpaCustomerRepository;
import ssginc_kdt_team3.BE.repository.customer.JpaDataCustomerRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

  private final JpaCustomerRepository customerRepository;

  // 회원가입
  @Transactional
  public CustomerJoinDTO join (CustomerJoinDTO customerJoinDTO) {
    validateDuplicateCustomer(customerJoinDTO); //중복회원 검증

    Customer customer = new Customer();
    customer.setEmail(customerJoinDTO.getEmail());
    customer.setPassword(customerJoinDTO.getPassword());
    customer.setName(customerJoinDTO.getName());
    customer.setPhoneNumber(customerJoinDTO.getPhone());
    customer.setAddress(customerJoinDTO.getAddress());
    customer.setGender(customerJoinDTO.getGender());

    //  검증됐으니까 역할, 상태 부여
    customer.setStatus(UserStatus.valueOf("ACTIVE"));
    customer.setRole(UserRole.CUSTOMER);

    customerRepository.save(customer);
    return customerJoinDTO;
  }

  public void validateDuplicateCustomer(CustomerJoinDTO customerJoinDTO) {
    Optional<Customer> findCustomer = customerRepository.findByEmail(customerJoinDTO.getEmail());

    if(findCustomer.isPresent()) {
      throw new IllegalStateException("이미 가입된 이메일입니다.");
    }
  }

  //로그인
  public Map login (CustomerLoginDTO customerLoginDTO) {
    String email = customerLoginDTO.getEmail();
    String password = customerLoginDTO.getPassword();
    Optional<Customer> customerInfo = customerRepository.findByEmail(email);

    // CustomerInfo가 널인지 아닌지 모르니까 Optional 을 isPresent로 체크한다
    if(customerInfo.isPresent()) { //null이 아니면(이메일 존재하면)
      log.info("이메일 일치");

      if (customerInfo.get().getPassword().equals(password)) { // .get으로 옵셔널 벗겨서 비교
        log.info("로그인 성공");
        Map map = new HashMap();
        map.put("id", customerInfo.get().getId());
        return map;
      } else {
        log.info("비밀번호 불일치 실패");
      }
    } else {
      log.info("아이디 X 실패");
    }
    return null;
  }

  // Email 찾기 : phone 으로 찾기
  public Customer getCustomerEmail(CustomerFindDTO customerFindDTO) {
    Customer customer = new Customer();
    customer.setPhoneNumber(customerFindDTO.getPhone());

    //이메일이 있다고 가정하고 -> phone과 일치하는 경우 (PK: 중복X)
    Optional<Customer> emailByPhone = customerRepository.findEmailByPhone(customer.getPhoneNumber());
    return emailByPhone.orElseThrow(() -> new NoSuchElementException("일치하는 정보가 없습니다."));
  }


  // PW 찾기
  public Customer getCustomerPassword (CustomerFindDTO customerFindDTO) {
    Customer customer = new Customer();
    customer.setEmail(customerFindDTO.getEmail());
    customer.setPhoneNumber(customerFindDTO.getPhone());

    Optional<Customer> emailAndPhone = customerRepository.findEmailAndPhone(customerFindDTO.getEmail(), customer.getPhoneNumber());
    System.out.println("emailAndPhone :" + emailAndPhone);

    return emailAndPhone.orElseThrow(() -> new NoSuchElementException("일치하는 정보가 없습니다."));
  }

  // 개인정보 변경
  @Transactional
  public void updateInfo(CustomerUpdateDTO customerUpdateDTO, Long id) { //이게 Customer의 id인지 어떻게 알아 ? 내가 repository findCustomer에 Long id 싸놔서 ...?
    // 로그인 후 -> Customer의 id 정보 필요
    Customer findCustomerId = customerRepository.findCustomer(id).get();
    findCustomerId.setPhoneNumber(customerUpdateDTO.getPhone());
    findCustomerId.setAddress(customerUpdateDTO.getAddress()); // 알아서 update 쿼리가 날아간다고 ..?
  }

  // PW 변경
  @Transactional
  public void updatePassword(PwdUpdateDTO pwdUpdateDTO, Long id) {
    Customer findCustomerId = customerRepository.findCustomer(id).get();

    // 기존 비밀번호 입력받아서 DB의 비밀번호와 일치하는지 확인
    boolean equals = pwdUpdateDTO.getPassword().equals(findCustomerId.getPassword());

    if(equals == true) {
      // 일치하면, 새로운 비밀번호 두 번 입력받고 둘이 일치하는지 검증
      String NewPassword = pwdUpdateDTO.getNewPassword();
      String PasswordConfirm = pwdUpdateDTO.getNewPasswordConfirm();

      if(NewPassword == PasswordConfirm) {
        findCustomerId.setPassword(pwdUpdateDTO.getNewPassword());
      } else {
        throw new IllegalStateException("입력한 비밀번호가 일치하지 않습니다.");
      }

    } else {
      throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
    }
  }


  // 등급조회

}
