package ssginc_kdt_team3.BE.service.admin;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
<<<<<<< HEAD
import ssginc_kdt_team3.BE.DTOs.customer.CustomeromerUpdateDTO;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.repository.Customer.JpaDateCustomerRepository;
=======
import ssginc_kdt_team3.BE.DTOs.cust.CustUpdateDTO;
import ssginc_kdt_team3.BE.domain.Cust;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.cust.JpaDateCustRepository;
>>>>>>> e626bc0e6d9f35c7568696371595dc31f268bfb7

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
<<<<<<< HEAD
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
=======
        Customer cust = new Customer();
        cust.setName("유저1");
        cust.setEmail("user1@user.com");
        cust.setPassword("qwer1234");
        cust.setPhoneNumber("01012341234");
        cust.setBirthday(LocalDate.now());
        cust.setGender(true);
        cust.setRole(UserRole.CUST);
        cust.setStatus(UserStatus.ACTIVE);

        Customer cust2 = new Customer();
        cust2.setName("유저2");
        cust2.setEmail("user2@user.com");
        cust2.setPassword("aaaa1234");
        cust2.setPhoneNumber("01011112222");
        cust2.setBirthday(LocalDate.now());
        cust2.setGender(true);
        cust2.setRole(UserRole.CUST);
        cust2.setStatus(UserStatus.ACTIVE);

        repository.save(cust);
        Customer save1 = repository.save(cust2);
>>>>>>> e626bc0e6d9f35c7568696371595dc31f268bfb7
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
<<<<<<< HEAD
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
=======
        Customer cust3 = new Customer();
        cust3.setName("유저3");
        cust3.setEmail("user3@user.com");
        cust3.setPassword("qwer1234");
        cust3.setPhoneNumber("01012341234");
        cust3.setBirthday(LocalDate.now());
        cust3.setGender(true);
        cust3.setRole(UserRole.CUST);
        cust3.setStatus(UserStatus.ACTIVE);

        Customer saveCust = repository.save(cust3);
        Long saveID = saveCust.getId();
>>>>>>> e626bc0e6d9f35c7568696371595dc31f268bfb7
        System.out.println(saveID);

        Optional<Customer> byId = repository.findById(saveID);

<<<<<<< HEAD
        Customer CustomerById = byId.get();
=======
        Customer custById = byId.get();
>>>>>>> e626bc0e6d9f35c7568696371595dc31f268bfb7

        assertThat(saveCustomer.getPassword()).isEqualTo(CustomerById.getPassword());
    }

    @Test
    public void updateTest() {
<<<<<<< HEAD
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
=======
        Customer cust3 = new Customer();
        cust3.setName("유저3");
        cust3.setEmail("user3@user.com");
        cust3.setPassword("qwer1234");
        cust3.setPhoneNumber("01012341234");
        cust3.setBirthday(LocalDate.now());
        cust3.setGender(true);
        cust3.setRole(UserRole.CUST);
        cust3.setStatus(UserStatus.ACTIVE);
        cust3.setGrade(null);

        Customer saveCust = repository.save(cust3);
        Long saveID = saveCust.getId();
>>>>>>> e626bc0e6d9f35c7568696371595dc31f268bfb7
        System.out.println(saveID);

        CustomeromerUpdateDTO CustomerDTO = new CustomeromerUpdateDTO();
        CustomerDTO.setName("hello");
        CustomerDTO.setPassword("zxc123");
        CustomerDTO.setPhone("01000000000");
        CustomerDTO.setStatus(UserStatus.QUIT);

        assertThat(CustomerService.updateCustomerInfo(saveID, CustomerDTO)).isTrue();

        CustomerService.updateCustomerInfo(saveID, CustomerDTO);

        Optional<Customer> byId = repository.findById(saveID);
<<<<<<< HEAD
        Customer CustomerById = byId.get();
=======
        Customer custById = byId.get();
>>>>>>> e626bc0e6d9f35c7568696371595dc31f268bfb7

        System.out.println("=========================CustomerById==============");

        assertThat(CustomerById.getPassword()).isEqualTo("zxc123");
        assertThat(CustomerById.getName()).isEqualTo("hello");

        System.out.println("=========================FIN==============");
    }



}