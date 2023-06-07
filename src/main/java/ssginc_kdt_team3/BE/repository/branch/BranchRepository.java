package ssginc_kdt_team3.BE.repository.branch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.internal.QueryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.admin.AdminBranchOwnerDTO;
import ssginc_kdt_team3.BE.DTOs.branch.BranchShopDTO;
import ssginc_kdt_team3.BE.domain.Branch;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
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
  public List<BranchShopDTO> BranchShopList(Long id) {

    String BranchShopQuery = "SELECT new ssginc_kdt_team3.BE.DTOs.branch.BranchShopDTO(s.id, s.name, s.location, s.shopImgUrl, s.status, s.category) FROM Shop s WHERE s.branch.id = :id";

    TypedQuery<BranchShopDTO> Shops = em.createQuery(BranchShopQuery, BranchShopDTO.class)
            .setParameter("id", id);

    List<BranchShopDTO> resultList = Shops.getResultList();

    return resultList;
  }

}
