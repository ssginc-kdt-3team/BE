package ssginc_kdt_team3.BE.service.cust;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.cust.CustJoinDTO;
import ssginc_kdt_team3.BE.DTOs.cust.CustLoginDTO;
import ssginc_kdt_team3.BE.domain.Cust;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.cust.JpaCustRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustServiceTest {
  @Autowired
  CustService custService;
  @Autowired
  JpaCustRepository custRepository;

  @Test
  void 회원가입() throws Exception {
    // given
    Cust cust = new Cust();
    cust.setEmail("test@gmail.com");
    cust.setPassword("1234");
    cust.setName("test");
    cust.setPhone("01012345678");
    cust.setRole(UserRole.valueOf("CUST"));
    cust.setStatus(UserStatus.valueOf("ACTIVE"));

    // when
    Cust saveId = custRepository.save(cust);

    // then
    assertThat(cust).isEqualTo(saveId);
  }

  @Test
  void 중복이메일_검증() throws Exception {
    // given : 프론트에서 넘어오는 값
    CustJoinDTO custJoinDTO1 = new CustJoinDTO();
    custJoinDTO1.setEmail("test@gmail.com");
    custJoinDTO1.setPassword("12345678");
    custJoinDTO1.setName("test");
    custJoinDTO1.setPhone("01012345678");

    CustJoinDTO custJoinDTO2 = new CustJoinDTO();
    custJoinDTO2.setEmail("test@gmail.com");
    custJoinDTO2.setPassword("12345678");
    custJoinDTO2.setName("test");
    custJoinDTO2.setPhone("01012345679");

    // when : Back
    custService.join(custJoinDTO1);
    try {
      custService.join(custJoinDTO2); //예외발생
    } catch (IllegalStateException e) {
      return;
    }

    // then
    fail("예외발생");
  }

  @Test
  void 로그인() throws NoSuchElementException {
    // given
    CustLoginDTO custLoginDTO = new CustLoginDTO();
    custLoginDTO.setEmail("test@gmail.com");
    custLoginDTO.setPassword("12345678");

    // when
    CustLoginDTO loginCust = custService.login(custLoginDTO);

    // then
    assertThat(custLoginDTO).isEqualTo(loginCust);

  }
}