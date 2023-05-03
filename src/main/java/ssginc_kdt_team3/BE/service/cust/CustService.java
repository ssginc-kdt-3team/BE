package ssginc_kdt_team3.BE.service.cust;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.cust.*;
import ssginc_kdt_team3.BE.domain.Cust;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.cust.JpaCustRepository;

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

  // Email 찾기 : name, password 로 찾기
  public String getCustEmail(CustFindDTO custFindDTO) { // DTO를 새로 만들어야 되...나 ?
    Cust cust = new Cust();
    cust.setName(custFindDTO.getName());
    cust.setPhone(custFindDTO.getPhone());

    Optional<Cust> findCustInfo = custRepository.findCust(cust.getId());

    if(!findCustInfo.isEmpty()) { //일치하는 값이 있으면
      return cust.getEmail();
    }
    return null; // 값이 없으면 여기 뭘 뿌려야 하지 ..
  }

  // PW 찾기
  public String getCustPassword (CustFindDTO custFindDTO) {
    Cust cust = new Cust();
    cust.setEmail(custFindDTO.getEmail());
    cust.setName(custFindDTO.getName());
    cust.setPhone(custFindDTO.getPhone());

    Optional<Cust> findCustInfo = custRepository.findCust(cust.getId());

    if(!findCustInfo.isEmpty()){ // 일치하는 값이 있으면
      return cust.getPassword();
    }
    return null;
  }

  // 개인정보 변경
  @Transactional
  public void updateInfo(InfoUpdateDTO infoUpdateDTO, Long id) { //이게 cust의 id인지 어떻게 알아 ? 내가 repository findCust에 Long id 싸놔서 ...?
    // 로그인 후 -> cust의 id 정보 필요
    Cust findCustId = custRepository.findCust(id).get(); // 왜 .get 안하고 Optional<Cust> 로 들어오면 set이 안 되지
    findCustId.setPhone(infoUpdateDTO.getPhone());
    findCustId.setAddress(infoUpdateDTO.getAddress()); // 알아서 update 쿼리가 날아간다고 ..?
  }

  // PW 변경
  @Transactional
  public void updatePassword(PwdUpdateDTO pwdUpdateDTO, Long id) {
    Cust findCustId = custRepository.findCust(id).get();

    // 기존 비밀번호 입력받아서 DB의 비밀번호와 일치하는지 확인
    boolean equals = pwdUpdateDTO.getPassword().equals(findCustId.getPassword());

    if(equals == true) {
      // 일치하면, 새로운 비밀번호 두 번 입력받고 둘이 일치하는지 검증
      String NewPassword = pwdUpdateDTO.getNewPassword();
      String PasswordConfirm = pwdUpdateDTO.getNewPasswordConfirm();

      if(NewPassword == PasswordConfirm) {
        findCustId.setPassword(pwdUpdateDTO.getNewPassword());
      } else {
        throw new IllegalStateException("입력한 비밀번호가 일치하지 않습니다.");
      }

    } else {
      throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
    }
  }


  // 등급조회


  // 지점조회


}
