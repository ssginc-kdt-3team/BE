package ssginc_kdt_team3.BE.repository.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssginc_kdt_team3.BE.domain.Branch;
import ssginc_kdt_team3.BE.domain.Reservation;


import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BranchRepository {
  private final EntityManager em;

  //조회: Branch에서 조회할거니까 <Store>
  public Optional<Branch> findBranch(Long id){
    return Optional.ofNullable(em.find(Branch.class, id));
  }

  public List<Branch> findAllBranch(){
    List<Branch> resultReserve = em.createQuery("SELECT b FROM Branch b", Branch.class)
        .getResultList();
    return resultReserve;
  }

}
