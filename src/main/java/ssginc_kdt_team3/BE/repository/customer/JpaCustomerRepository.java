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

  @Override
  public Optional<Customer> findEmailByPhone(String phoneNumber) {
    return em.createQuery("SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber", Customer.class)
        .setParameter("phoneNumber", phoneNumber)
        .getResultList()
        .stream()
        .findAny();
  }

//  public List<Customer> findByGrade (Long CustomerId) { // 등급조회
//    return em.createQuery("SELECT c FROM Customer c INNER JOIN Grade g ON c.gId = g.gId WHERE g.getName() = :name", Customer.class)
//        .setParameter("name", name)
//        .getResultList()
//        .stream()
//        .findAny();
//  }
//
//  public Grade findGradeId(Long id) {
//    return em.createQuery("SELECT c FROM Customer c join Grade g ON c.grade.id = g.id WHERE g.name=:gradeName()", Customer.class)
//        .setParameter()
//        .getResultList()
//        .stream()
//        .findAny();
//  }

}
