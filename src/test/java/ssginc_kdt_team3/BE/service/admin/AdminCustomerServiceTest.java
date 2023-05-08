package ssginc_kdt_team3.BE.service.admin;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerUpdateDTO;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.customer.JpaDateCustomerRepository;


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
        Customer customer = new Customer();
        customer.setName("유저1");
        customer.setPassword("qwer1234");
        customer.setPhoneNumber("01012341234");
        customer.setBirthday(LocalDate.now());
        customer.setGender(true);
        customer.setRole(UserRole.CUSTOMER);
        customer.setStatus(UserStatus.ACTIVE);

        Customer customer2 = new Customer();
        customer2.setName("유저2");
        customer2.setEmail("user2@user.com");
        customer2.setPassword("aaaa1234");
        customer2.setPhoneNumber("01011112222");
        customer2.setBirthday(LocalDate.now());
        customer2.setGender(true);
        customer2.setRole(UserRole.CUSTOMER);
        customer2.setStatus(UserStatus.ACTIVE);

        repository.save(customer);
        Customer save1 = repository.save(customer2);

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

        Customer customer3 = new Customer();
        customer3.setName("유저3");
        customer3.setEmail("user3@user.com");
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

        Customer customer3 = new Customer();
        customer3.setName("유저3");
        customer3.setEmail("user3@user.com");
        customer3.setPassword("qwer1234");
        customer3.setPhoneNumber("01012341234");
        customer3.setBirthday(LocalDate.now());
        customer3.setGender(true);
        customer3.setRole(UserRole.CUSTOMER);
        customer3.setStatus(UserStatus.ACTIVE);
        customer3.setGrade(null);

        Customer saveCustomer = repository.save(customer3);
        Long saveID = saveCustomer.getId();

        System.out.println(saveID);

        CustomerUpdateDTO CustomerDTO = new CustomerUpdateDTO();
        CustomerDTO.setName("hello");
        CustomerDTO.setPassword("zxc123");
        CustomerDTO.setPhone("01000000000");
        CustomerDTO.setStatus(UserStatus.QUIT);

        assertThat(CustomerService.updateCustomerInfo(saveID, CustomerDTO)).isTrue();

        CustomerService.updateCustomerInfo(saveID, CustomerDTO);

        Optional<Customer> byId = repository.findById(saveID);
        Customer customerById = byId.get();


        System.out.println("=========================CustomerById==============");

        assertThat(customerById.getPassword()).isEqualTo("zxc123");
        assertThat(customerById.getName()).isEqualTo("hello");

        System.out.println("=========================FIN==============");
    }



}