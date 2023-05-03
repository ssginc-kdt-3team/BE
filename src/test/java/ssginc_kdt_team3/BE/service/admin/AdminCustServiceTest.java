package ssginc_kdt_team3.BE.service.admin;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.cust.CustListDTO;
import ssginc_kdt_team3.BE.DTOs.cust.InfoUpdateDTO;
import ssginc_kdt_team3.BE.domain.Cust;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.cust.JpaDateCustRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class AdminCustServiceTest {

    @Autowired
    JpaDateCustRepository repository;

    @Autowired
    AdminCustService custService = new AdminCustService(repository);

    @BeforeEach
    public void make() {
        Cust cust = new Cust();
        cust.setName("유저1");
        cust.setEmail("user1@user.com");
        cust.setPassword("qwer1234");
        cust.setPhone("01012341234");
        cust.setBirthday(LocalDate.now());
        cust.setGender(true);
        cust.setRole(UserRole.CUST);
        cust.setStatus(UserStatus.ACTIVE);

        Cust cust2 = new Cust();
        cust2.setName("유저2");
        cust2.setEmail("user2@user.com");
        cust2.setPassword("aaaa1234");
        cust2.setPhone("01011112222");
        cust2.setBirthday(LocalDate.now());
        cust2.setGender(true);
        cust2.setRole(UserRole.CUST);
        cust2.setStatus(UserStatus.ACTIVE);

        repository.save(cust);
        Cust save1 = repository.save(cust2);
    }

    @AfterEach
    public void clean() {
        repository.deleteAll();
    }

    @Test
    public void findAllTest() {

        List<CustListDTO> all = custService.findAllCust();

        int len = all.size();

        CustListDTO custListDTO = all.get(0);

        String email = custListDTO.getEmail();

        assertThat(len).isEqualTo(2);
        assertThat(email).isEqualTo("user1@user.com");
    }

    @Test
    public void findOneTest() {
        Cust cust3 = new Cust();
        cust3.setName("유저3");
        cust3.setEmail("user3@user.com");
        cust3.setPassword("qwer1234");
        cust3.setPhone("01012341234");
        cust3.setBirthday(LocalDate.now());
        cust3.setGender(true);
        cust3.setRole(UserRole.CUST);
        cust3.setStatus(UserStatus.ACTIVE);

        Cust saveCust = repository.save(cust3);
        Long saveID = saveCust.getId();

        Cust findCust = custService.findCustById(saveID).get();

        assertThat(saveCust.getPassword()).isEqualTo(findCust.getPassword());
    }

    @Test
    public void updateTest() {
        Cust cust3 = new Cust();
        cust3.setName("유저3");
        cust3.setEmail("user3@user.com");
        cust3.setPassword("qwer1234");
        cust3.setPhone("01012341234");
        cust3.setBirthday(LocalDate.now());
        cust3.setGender(true);
        cust3.setRole(UserRole.CUST);
        cust3.setStatus(UserStatus.ACTIVE);

        Cust saveCust = repository.save(cust3);
        Long saveID = saveCust.getId();

        InfoUpdateDTO custDTO = new InfoUpdateDTO();
        custDTO.setName("hello");
        custDTO.setPassword("zxc123");
        custDTO.setPhone("01000000000");
        custDTO.setStatus(UserStatus.QUIT);

        assertThat(custService.updateCustInfo(saveID, custDTO)).isTrue();

        Cust cust = custService.findCustById(saveID).get();

        assertThat(cust.getPassword()).isEqualTo("zxc123");
        assertThat(cust.getName()).isEqualTo("hello");


    }



}