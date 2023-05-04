package ssginc_kdt_team3.BE.repository.cust;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssginc_kdt_team3.BE.domain.Cust;
import ssginc_kdt_team3.BE.repository.interfaces.cust.CustRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaCustRepository implements CustRepository {
  private final EntityManager em;

  //저장
  @Override
  public Cust save(Cust cust){
    em.persist(cust);
    return cust; //이건 받아온 cust 그대로 리턴해서 참일 수밖에 없어
  }

  //조회
  @Override
  public Optional<Cust> findCust(Long id){
    return Optional.ofNullable(em.find(Cust.class, id));
  }

  //전체조회
  @Override
  public List<Cust> findAll(){
    List<Cust> resultList = em.createQuery("SELECT c FROM Cust c", Cust.class)
        .getResultList();
    return resultList;
  }

  //로그인
  @Override
  public Optional<Cust> findByEmail(String email) {
    return em.createQuery("SELECT c FROM Cust c WHERE c.email = :email", Cust.class)
        .setParameter("email", email)
        .getResultList()
        .stream()
        .findAny();
  }

  @Override
  public void update(Long id, Cust cust) {
  }


}
