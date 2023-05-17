package ssginc_kdt_team3.BE.repository.branch;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssginc_kdt_team3.BE.DTOs.branch.BranchShopDTO;
import ssginc_kdt_team3.BE.domain.Branch;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BranchRepository {
  private final EntityManager em;

  // 조회
  public Branch findBranch(Long id) {
    return em.find(Branch.class, id);
  }

  // 전체 지점조회
  public List<Branch> findAllBranch() {
    List<Branch> resultReserve = em.createQuery("SELECT b FROM Branch b", Branch.class)
            .getResultList();
    return resultReserve;
  }

  //지점별 매장 조회
  public List<BranchShopDTO> BranchShopList(long id) {


    String BranchShopQuery = "SELECT new ssginc_kdt_team3.BE.DTOs.branch.BranchShopDTO(s.id, s.name, s.location, s.shopImg, s.status) FROM Shop s WHERE s.branch.id = :id";



    TypedQuery<BranchShopDTO> Shops = em.createQuery(BranchShopQuery, BranchShopDTO.class)
            .setParameter("id", id);

    List<BranchShopDTO> resultList = Shops.getResultList();

    return resultList;
  }

}
