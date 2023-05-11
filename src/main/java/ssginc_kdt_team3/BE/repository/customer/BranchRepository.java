package ssginc_kdt_team3.BE.repository.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssginc_kdt_team3.BE.domain.Branch;


import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BranchRepository {
  private final EntityManager em;

  //조회: Branch에서 조회할거니까 <Store>
  public Optional<Branch> findBranch(Long id){
    return Optional.ofNullable(em.find(Branch.class, id));
  }

}
