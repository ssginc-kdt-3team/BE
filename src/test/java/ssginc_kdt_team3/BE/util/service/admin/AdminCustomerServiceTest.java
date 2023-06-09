package ssginc_kdt_team3.BE.util.service.admin;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ssginc_kdt_team3.BE.DTOs.Address;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerListDTO;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerUpdateDTO;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Grade;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.customer.JpaDataCustomerRepository;
import ssginc_kdt_team3.BE.service.admin.AdminCustomerService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AdminCustomerServiceTest {

    @Autowired
    JpaDataCustomerRepository repository;

    @Autowired
    AdminCustomerService CustomerService = new AdminCustomerService(repository);

    @AfterEach
    public void clean() {
        repository.deleteAll();
    }
    @BeforeEach
    public void make() {
        Customer customer = new Customer();
        customer.setName("유저1");
        customer.setEmail("user1@naver.com");
        customer.setPassword("qwer1234");
        customer.setPhoneNumber("01012341234");
        customer.setBirthday(LocalDate.now());
        customer.setGender(true);
        customer.setRole(UserRole.CUSTOMER);
        customer.setStatus(UserStatus.ACTIVE);
        Grade existingGrade = repository.gradeFindById(1L);
        customer.setGrade(existingGrade);

        Customer customer2 = new Customer();
        customer2.setName("유저2");
        customer2.setEmail("user2@gmail.com");
        customer2.setPassword("aaaa1234");
        customer2.setPhoneNumber("01011112222");
        customer2.setBirthday(LocalDate.now());
        customer2.setGender(true);
        customer2.setRole(UserRole.CUSTOMER);
        customer2.setStatus(UserStatus.ACTIVE);
        customer2.setGrade(existingGrade);
        repository.save(customer);
        repository.save(customer2);
    }

    @Test
    public void findAllTest() {

        Pageable pageable = PageRequest.of(0, 5);

        Page<CustomerListDTO> allCustomer = CustomerService.findAllCustomer(pageable);

        if (allCustomer.hasContent())
        {
            List<CustomerListDTO> listCustomer = allCustomer.getContent();
            CustomerListDTO customerListDTO = listCustomer.get(0);
            String email = customerListDTO.getEmail();
            int len = listCustomer.size();
            assertThat(len).isEqualTo(2);
            assertThat(email).isEqualTo("user1@naver.com");
        }
        else {
            System.out.println("비어있음");
        }
    }

    @Test
    public void findOneTest() {

        Customer customer3 = new Customer();
        customer3.setName("김태윤");
        customer3.setEmail("tpxja@gmail.com");
        customer3.setPassword("qwer1234");
        customer3.setPhoneNumber("01012341234");
        customer3.setBirthday(LocalDate.now());
        customer3.setGender(true);
        customer3.setRole(UserRole.CUSTOMER);
        customer3.setStatus(UserStatus.ACTIVE);

        Customer saveCustomer = repository.save(customer3);
        Long saveID = saveCustomer.getId();
        System.out.println(saveID);

        Optional<Customer> byId = repository.findById(saveID);

        Customer customerById = byId.get();

        assertThat(saveCustomer.getPassword()).isEqualTo(customerById.getPassword());
    }

    @Test
    public void updateTest() {

        Customer customer4 = new Customer();
        customer4.setName("사용자");
        customer4.setEmail("user8@naver.com");
        customer4.setPassword("qwer1234");
        customer4.setPhoneNumber("01012341234");
        customer4.setBirthday(LocalDate.now());
        customer4.setGender(true);
        customer4.setRole(UserRole.CUSTOMER);
        customer4.setStatus(UserStatus.ACTIVE);
        customer4.setGrade(null);

        Customer saveCustomer = repository.save(customer4);
        Long saveID = saveCustomer.getId();

        System.out.println(saveID);

        CustomerUpdateDTO CustomerDTO = new CustomerUpdateDTO();
        CustomerDTO.setName("hello");
        CustomerDTO.setPassword("zxc123");
        CustomerDTO.setPhone("01000000000");
        CustomerDTO.setStatus(UserStatus.QUIT);

        Address address = new Address("부산시","해운대구","센텀리더스 마크","12345");
        CustomerDTO.setAddress(address);
        //주소 설정

        Grade existingGrade = repository.gradeFindById(1L);
        System.out.println(existingGrade.getName());
        CustomerDTO.setGrade(existingGrade);
        //등급 설정

        assertThat(CustomerService.updateCustomerInfo(saveID, CustomerDTO)).isTrue();
        //↑ AdminCustomerService 클래스에서 @Transactional 이 있어서
        //클래스 내부의 updateCustomerInfo 메서드가 리턴되는 순간 롤백되면서 에러가 납니다 ㅜㅜ
        //테스트하는 동안에는 @Transactional 을 주석처리하면 잘 돌아 갑니다.
        CustomerService.updateCustomerInfo(saveID, CustomerDTO);

        Optional<Customer> byId = repository.findById(saveID);
        Customer customerById = byId.get();

        System.out.println("=========================CustomerById==============");

        assertThat(customerById.getPassword()).isEqualTo("qwer1234");
        assertThat(customerById.getName()).isEqualTo("사용자");

        System.out.println("=========================FIN==============");
    }



}