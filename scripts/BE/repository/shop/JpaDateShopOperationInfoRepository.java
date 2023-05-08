package ssginc_kdt_team3.BE.repository.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.domain.ShopOperationInfo;

public interface JpaDateShopOperationInfoRepository extends JpaRepository<ShopOperationInfo, Long> {

}
