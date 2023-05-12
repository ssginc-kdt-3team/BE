package ssginc_kdt_team3.BE.repository.cust;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssginc_kdt_team3.BE.domain.Cust;
import ssginc_kdt_team3.BE.domain.Store;
import ssginc_kdt_team3.BE.repository.interfaces.cust.CustRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StoreRepository {
  private final EntityManager em;

  //조회: Store 에서 조회할거니까 <Store>
  public Optional<Store> findStore(Long id){
    return Optional.ofNullable(em.find(Store.class, id));
  }

}
