package ssginc_kdt_team3.BE.repository.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Grade;
import ssginc_kdt_team3.BE.repository.interfaces.customer.CustomerRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaCustomerRepository implements CustomerRepository {
  private final EntityManager em;

  //저장
  @Override
  public Customer save(Customer customer){
    em.persist(customer);
    return customer;
  }

  //조회
  @Override
  public Optional<Customer> findCustomer(Long id){
    return Optional.ofNullable(em.find(Customer.class, id));
  }

  //전체조회
  @Override
  public List<Customer> findAll(){
    List<Customer> resultList = em.createQuery("SELECT c FROM Customer c", Customer.class)
        .getResultList();
    return resultList;
  }

  //로그인
  @Override
  public Optional<Customer> findByEmail(String email) {
    return em.createQuery("SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
        .setParameter("email", email)
        .getResultList()
        .stream()
        .findAny();
  }

  @Override
  public void update(Long id, Customer Customer) {
  }

  // ID 찾기
  @Override
  public Optional<Customer> findEmailByPhone(String phone) {
    return em.createQuery("SELECT c FROM Customer c WHERE c.phoneNumber = :phone", Customer.class)
        .setParameter("phone", phone)
        .getResultList()
        .stream()
        .findAny();
  }

  // PW 찾기
  public Optional<Customer> findEmailAndPhone(String email, String phone){
    return em.createQuery("SELECT c FROM Customer c WHERE c.email = :email AND c.phoneNumber = :phone", Customer.class)
        .setParameter("email", email)
        .setParameter("phone", phone)
        .getResultList()
        .stream()
        .findAny();
  }

}
