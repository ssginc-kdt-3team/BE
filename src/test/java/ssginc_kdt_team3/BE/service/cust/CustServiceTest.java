package ssginc_kdt_team3.BE.service.cust;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.cust.CustFindDTO;
import ssginc_kdt_team3.BE.DTOs.cust.CustJoinDTO;
import ssginc_kdt_team3.BE.DTOs.cust.CustLoginDTO;
import ssginc_kdt_team3.BE.domain.Cust;
import ssginc_kdt_team3.BE.domain.Store;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.cust.JpaCustRepository;
import ssginc_kdt_team3.BE.repository.cust.StoreRepository;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional

class CustServiceTest {
  @Autowired
  CustService custService;
  @Autowired
  JpaCustRepository custRepository;

  @Autowired
  StoreRepository storeRepository;

  @Test
  void 회원가입() throws Exception {
    // given
    Cust cust = new Cust();
    cust.setEmail("test@gmail.com");
    cust.setPassword("12345678");
    cust.setName("test");
    cust.setPhone("01012345678");
    cust.setRole(UserRole.CUST);
    cust.setStatus(UserStatus.ACTIVE);
    cust.setGrade(null);

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
  void 로그인() {
    // given
    CustLoginDTO custLoginDTO = new CustLoginDTO();
    custLoginDTO.setEmail("user1@user.com");
    custLoginDTO.setPassword("qwer1234");

    // when
    boolean loginCust = custService.login(custLoginDTO);

    // then
    assertThat(loginCust).isEqualTo(true);
  }


  @Test
  void 이메일찾기() {
    CustFindDTO custFindDTO = new CustFindDTO();
    custFindDTO.setPhone("01012345678");

    Cust custEmail = custService.getCustEmail(custFindDTO);

//    assertThat(custEmail).isEqualTo(custFindDTO.getEmail()); // phone만 넣었으니까 여긴 email 정보가없어
    System.out.println(custEmail.getEmail());
    assertThat(custEmail).isEqualTo("abc@abc.abc");

  }

  @Test
  void 비밀번호_찾기() {
  }

  @Test
  void 개인정보_변경() {
    // given
    Cust custId = custRepository.findCust(8L).get();// Long 타입은 직접 값을 넣을 때 L 붙여줘야 돼
// findCust로 DB에 있는걸 넣어줘야지

    custId.setPhone("test");

    assertThat(custId.getPhone()).isEqualTo("test");

  }

  @Test
  void 비밀번호_변경() {

    Cust custId = custRepository.findCust(8L).get();

    custId.setPassword("abc");

    assertThat(custId.getPassword()).isEqualTo("abc");

  }

  @Test
  void findStore() {
    Store store = custService.findStore(1L).get();
    System.out.println("store :" +store);


  }
}