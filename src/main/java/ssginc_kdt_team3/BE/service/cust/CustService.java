package ssginc_kdt_team3.BE.service.cust;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.cust.*;
import ssginc_kdt_team3.BE.domain.Cust;
import ssginc_kdt_team3.BE.domain.Store;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.cust.JpaCustRepository;
import ssginc_kdt_team3.BE.repository.cust.StoreRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustService {

  private final JpaCustRepository custRepository;
  private final StoreRepository storeRepository;

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
  public boolean login (CustLoginDTO custLoginDTO) {
    String email = custLoginDTO.getEmail();
    String password = custLoginDTO.getPassword();
    Optional<Cust> custInfo = custRepository.findByEmail(email);

    // custInfo가 널인지 아닌지 모르니까 Optional 을 isPresent로 체크한다
    if(custInfo.isPresent()) { //null이 아니면(이메일 존재하면)
      log.info("이메일 일치");

      if(custInfo.get().getPassword().equals(password)) { // .get으로 옵셔널 벗겨서 비교
        log.info("로그인 성공");
        return true;
      }

    } else {
      log.info("로그인 실패");
    }
    return false;
  }

  // Email 찾기 : phone 으로 찾기
  public Cust getCustEmail(CustFindDTO custFindDTO) {
    Cust cust = new Cust();
    cust.setPhone(custFindDTO.getPhone());

    //이메일이 있다고 가정하고 -> phone과 일치하는 경우 (PK: 중복X)
    Optional<Cust> emailByPhone = custRepository.findEmailByPhone(cust.getPhone());
    return emailByPhone.orElseThrow(() -> new NoSuchElementException("일치하는 정보가 없습니다."));
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
  public void updateInfo(CustUpdateDTO custUpdateDTO, Long id) { //이게 cust의 id인지 어떻게 알아 ? 내가 repository findCust에 Long id 싸놔서 ...?
    // 로그인 후 -> cust의 id 정보 필요
    Cust findCustId = custRepository.findCust(id).get();
    findCustId.setPhone(custUpdateDTO.getPhone());
    findCustId.setAddress(custUpdateDTO.getAddress()); // 알아서 update 쿼리가 날아간다고 ..?
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
  public Optional<Store> findStore(Long id) {
    Optional<Store> store = storeRepository.findStore(id);
    return store;
  }

}
