package ssginc_kdt_team3.BE.service.cust;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.cust.CustJoinDTO;
import ssginc_kdt_team3.BE.DTOs.cust.CustLoginDTO;
import ssginc_kdt_team3.BE.domain.Cust;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.cust.JpaCustRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustService {

  private final JpaCustRepository custRepository;

  // 회원가입
  @Transactional
  public CustJoinDTO join (CustJoinDTO custJoinDTO) {
    validateDuplicateCust(custJoinDTO); //중복회원 검증

    Cust cust = new Cust();
    cust.setEmail(custJoinDTO.getEmail());
    cust.setPassword(custJoinDTO.getPassword());
    cust.setName(custJoinDTO.getName());
    cust.setPhone(custJoinDTO.getPhone());

    //  검증됐으니까 역할, 상태 부여
    cust.setStatus(UserStatus.valueOf("ACTIVE"));
    cust.setRole(UserRole.valueOf("CUST"));

    custRepository.save(cust);
    return custJoinDTO;
  }

  private void validateDuplicateCust(CustJoinDTO custJoinDTO) {
    Optional<Cust> findCust = custRepository.findByEmail(custJoinDTO.getEmail());

    if(!findCust.isEmpty()) {
      throw new IllegalStateException("이미 가입된 이메일입니다.");
    }
  }

  //로그인
  public CustLoginDTO login (CustLoginDTO custLoginDTO) {
    String email = custLoginDTO.getEmail();
    String password = custLoginDTO.getPassword();
    Optional<Cust> CustInfo = custRepository.findByEmail(email);

    if(CustInfo.equals(email) && CustInfo.equals(password)) {
      System.out.println("로그인 성공");
    } else {
      System.out.println("로그인 실패");
    }
    return custLoginDTO;
  }

}
