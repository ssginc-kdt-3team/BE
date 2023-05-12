package ssginc_kdt_team3.BE.repository.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssginc_kdt_team3.BE.domain.Shop;

public interface JpaDateShopRepository extends JpaRepository<Shop, Long> {

    @Query("SELECT s FROM Shop s " +
            " JOIN store str ON s.store.id = str.id JOIN ShopOperationInfo i ON s.operationInfo.id = i.id" +
            " WHERE str.id = :storeId")
    Page<Shop> findAllByStoreId (@Param("storeId") Long storeId, Pageable pageable);

}
