package ssginc_kdt_team3.BE.service.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import ssginc_kdt_team3.BE.DTOs.customer.CustomerFindDTO;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerJoinDTO;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerLoginDTO;
import ssginc_kdt_team3.BE.domain.Branch;
import ssginc_kdt_team3.BE.domain.Customer;

import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.customer.BranchRepository;
import ssginc_kdt_team3.BE.repository.customer.JpaCustomerRepository;
import ssginc_kdt_team3.BE.repository.customer.JpaDateCustomerRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional

class CustomerServiceTest {
  @Autowired
  CustomerService customerService;
  @Autowired
  JpaCustomerRepository customerRepository;

  @Autowired
  BranchRepository branchRepository;

  @Test
  @Rollback(value = false)
  void 회원가입() throws Exception {
    // given
    Customer customer = new Customer();
    customer.setEmail("test@gmail.com");
    customer.setPassword("12345678");
    customer.setName("test");
    customer.setPhoneNumber("01012345678");
    customer.setRole(UserRole.CUSTOMER);
    customer.setStatus(UserStatus.ACTIVE);
    customer.setGrade(null);

    // when
    Customer saveId = customerRepository.save(customer);
    System.out.println("saveId :" +saveId);

    // then
    assertThat(customer).isEqualTo(saveId);
  }

  @Test
  void 중복이메일_검증() throws Exception {
    // given : 프론트에서 넘어오는 값
    CustomerJoinDTO customerJoinDTO1 = new CustomerJoinDTO();
    customerJoinDTO1.setEmail("test@gmail.com");
    customerJoinDTO1.setPassword("12345678");
    customerJoinDTO1.setName("test");
    customerJoinDTO1.setPhone("01012345678");

    CustomerJoinDTO customerJoinDTO2 = new CustomerJoinDTO();
    customerJoinDTO2.setEmail("test@gmail.com");
    customerJoinDTO2.setPassword("12345678");
    customerJoinDTO2.setName("test");
    customerJoinDTO2.setPhone("01012345679");

    // when : Back
    customerService.join(customerJoinDTO1);
    try {
      customerService.join(customerJoinDTO2); //예외발생
    } catch (IllegalStateException e) {
      return;
    }

    // then
    fail("예외발생");
  }

  @Test
  void 로그인() {
    // given
    CustomerLoginDTO customerLoginDTO = new CustomerLoginDTO();
    customerLoginDTO.setEmail("user1@user.com");
    customerLoginDTO.setPassword("qwer1234");

    // when
    boolean loginCustomer = customerService.login(customerLoginDTO);

    // then
    assertThat(loginCustomer).isEqualTo(true);
  }


  @Test
  void 이메일찾기() {
    CustomerFindDTO customerFindDTO = new CustomerFindDTO();
    customerFindDTO.setPhone("01012345678");

    Customer customerEmail = customerService.getCustomerEmail(customerFindDTO);

    System.out.println(customerEmail.getEmail());
    assertThat(customerEmail.getEmail()).isEqualTo("test@gmail.com");

  }

  @Test
  void 비밀번호_찾기() {
    CustomerFindDTO customerFindDTO = new CustomerFindDTO();
    customerFindDTO.setEmail("test@gmail.com");
    customerFindDTO.setPhone("01012345678");

    Customer customerPassword = customerService.getCustomerPassword(customerFindDTO);
    System.out.println("customerPassword :" + customerPassword.getPassword());

    assertThat(customerPassword.getPassword()).isEqualTo("12345678");
  }

  @Test
  void 개인정보_변경() {
    // given
    Customer customerId = customerRepository.findCustomer(8L).get();// Long 타입은 직접 값을 넣을 때 L 붙여줘야 돼
// findCustomer로 DB에 있는걸 넣어줘야지

    customerId.setPhoneNumber("test");

    assertThat(customerId.getPhoneNumber()).isEqualTo("test");

  }

  @Test
  void 비밀번호_변경() {

    Customer customerId = customerRepository.findCustomer(8L).get();

    customerId.setPassword("abc");

    assertThat(customerId.getPassword()).isEqualTo("abc");

  }

  @Test
  void findStore() {
    Branch branch = customerService.findBranch(1L).get();
    System.out.println("branch :" +branch);

  }

  @Test
  void findAllBranch() {
    List<Branch> allBranch = customerService.findAllBranch();
    System.out.println("allBranch============>" + allBranch.size());

  }
}