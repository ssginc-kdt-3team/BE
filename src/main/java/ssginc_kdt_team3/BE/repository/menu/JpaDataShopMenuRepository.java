package ssginc_kdt_team3.BE.repository.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssginc_kdt_team3.BE.domain.ShopMenu;

import java.util.List;

@Repository
public interface JpaDataShopMenuRepository extends JpaRepository<ShopMenu, Long> {

    List<ShopMenu> findAllByShop_Id(Long shopId);
}
