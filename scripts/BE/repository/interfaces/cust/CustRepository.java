package ssginc_kdt_team3.BE.repository.interfaces.cust;

import ssginc_kdt_team3.BE.domain.Cust;
import ssginc_kdt_team3.BE.domain.Grade;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CustRepository {
  public Cust save (Cust cust); //저장
  public Optional<Cust> findCust(Long id); //조회
  public List<Cust> findAll(); //전체조회
  public Optional<Cust> findByEmail(String email); //이메일 조회 -> 로그인
  public void update (Long id, Cust cust); //수정
  public Optional<Cust> findEmailByPhone(String phone); // 이메일찾기


}
