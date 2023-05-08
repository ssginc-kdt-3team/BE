package ssginc_kdt_team3.BE.repository.owner.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssginc_kdt_team3.BE.DTOs.cust.CustListDTO;
import ssginc_kdt_team3.BE.domain.Admin;
import ssginc_kdt_team3.BE.domain.Shop;

import java.util.List;
import java.util.Optional;

public interface JpaDateShopRepository extends JpaRepository<Shop, Long> {

    @Query("select s from Shop s " +
            "join Store str on s.store.id = str.id join ShopOperationInfo i on s.operationInfo.id = i.id" +
            " where str.id = :storeId")
    Page<Shop> findAllByStoreId (@Param("storeId") Long storeId, Pageable pageable);

}
