package ssginc_kdt_team3.BE.service.admin;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.customer.CustomeromerUpdateDTO;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.repository.Customer.JpaDateCustomerRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class AdminCustomerServiceTest {

    @Autowired
    JpaDateCustomerRepository repository;

    @Autowired
    AdminCustomerService CustomerService = new AdminCustomerService(repository);

    @BeforeEach
    public void make() {
        Customer Customer = new Customer();
        Customer.setName("유저1");
        Customer.setEmail("user1@user.com");
        Customer.setPassword("qwer1234");
        Customer.setPhone("01012341234");
        Customer.setBirthday(LocalDate.now());
        Customer.setGender(true);
        Customer.setRole(UserRole.Customer);
        Customer.setStatus(UserStatus.ACTIVE);

        Customer Customer2 = new Customer();
        Customer2.setName("유저2");
        Customer2.setEmail("user2@user.com");
        Customer2.setPassword("aaaa1234");
        Customer2.setPhone("01011112222");
        Customer2.setBirthday(LocalDate.now());
        Customer2.setGender(true);
        Customer2.setRole(UserRole.Customer);
        Customer2.setStatus(UserStatus.ACTIVE);

        repository.save(Customer);
        Customer save1 = repository.save(Customer2);
    }

//    @AfterEach
//    public void clean() {
//        repository.deleteAll();
//    }

//    @Test
//    public void findAllTest() {
//        Pageable pageable = PageRequest.of(0, 5);
//        Page<CustomerListDTO> allCustomer = CustomerService.findAllCustomer(pageable);
//
//
//        int len = allCustomer.size();
//
//        CustomerListDTO CustomerListDTO = all.get(0);
//
//        String email = CustomerListDTO.getEmail();
//
//        assertThat(len).isEqualTo(2);
//        assertThat(email).isEqualTo("user1@user.com");
//    }

    @Test
    public void findOneTest() {
        Customer Customer3 = new Customer();
        Customer3.setName("유저3");
        Customer3.setEmail("user3@user.com");
        Customer3.setPassword("qwer1234");
        Customer3.setPhone("01012341234");
        Customer3.setBirthday(LocalDate.now());
        Customer3.setGender(true);
        Customer3.setRole(UserRole.Customer);
        Customer3.setStatus(UserStatus.ACTIVE);

        Customer saveCustomer = repository.save(Customer3);
        Long saveID = saveCustomer.getId();
        System.out.println(saveID);

        Optional<Customer> byId = repository.findById(saveID);

        Customer CustomerById = byId.get();

        assertThat(saveCustomer.getPassword()).isEqualTo(CustomerById.getPassword());
    }

    @Test
    public void updateTest() {
        Customer Customer3 = new Customer();
        Customer3.setName("유저3");
        Customer3.setEmail("user3@user.com");
        Customer3.setPassword("qwer1234");
        Customer3.setPhone("01012341234");
        Customer3.setBirthday(LocalDate.now());
        Customer3.setGender(true);
        Customer3.setRole(UserRole.Customer);
        Customer3.setStatus(UserStatus.ACTIVE);
        Customer3.setGrade(null);

        Customer saveCustomer = repository.save(Customer3);
        Long saveID = saveCustomer.getId();
        System.out.println(saveID);

        CustomeromerUpdateDTO CustomerDTO = new CustomeromerUpdateDTO();
        CustomerDTO.setName("hello");
        CustomerDTO.setPassword("zxc123");
        CustomerDTO.setPhone("01000000000");
        CustomerDTO.setStatus(UserStatus.QUIT);

        assertThat(CustomerService.updateCustomerInfo(saveID, CustomerDTO)).isTrue();

        CustomerService.updateCustomerInfo(saveID, CustomerDTO);

        Optional<Customer> byId = repository.findById(saveID);
        Customer CustomerById = byId.get();

        System.out.println("=========================CustomerById==============");

        assertThat(CustomerById.getPassword()).isEqualTo("zxc123");
        assertThat(CustomerById.getName()).isEqualTo("hello");

        System.out.println("=========================FIN==============");
    }



}