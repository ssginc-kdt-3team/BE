package ssginc_kdt_team3.BE.repository.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssginc_kdt_team3.BE.domain.Shop;

import java.util.List;

public interface JpaShopRepository extends JpaRepository<Shop, Long> {

    @Query("select s from Shop s where s.branch.id = :id")
    List<Shop> findBranchShop(@Param("id") Long id);
}
