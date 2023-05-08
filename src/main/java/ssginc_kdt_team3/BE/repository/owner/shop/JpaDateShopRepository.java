package ssginc_kdt_team3.BE.repository.owner.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ssginc_kdt_team3.BE.domain.Shop;


public interface JpaDateShopRepository extends JpaRepository<Shop, Long> {

    @Query("select s from Shop s " +
            "join Branch br on s.branch.id = br.id join ShopOperationInfo i on s.operationInfo.id = i.id" +
            " where br.id = :storeId")
    Page<Shop> findAllByStoreId (@Param("storeId") Long storeId, Pageable pageable);

}
