package ssginc_kdt_team3.BE.repository.interfaces.customer;

import ssginc_kdt_team3.BE.domain.Customer;


import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
  public Customer save (Customer customer); //저장
  public Optional<Customer> findCustomer(Long id); //조회
  public List<Customer> findAll(); //전체조회
  public Optional<Customer> findByEmail(String email); //이메일 조회 -> 로그인
  public void update (Long id, Customer customer); //수정
  public Optional<Customer> findEmailByPhone(String phoneNumber); // 이메일찾기


}
