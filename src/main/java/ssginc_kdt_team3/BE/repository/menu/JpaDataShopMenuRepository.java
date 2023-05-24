package ssginc_kdt_team3.BE.repository.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import ssginc_kdt_team3.BE.domain.ShopMenu;

import java.util.List;

public interface JpaDataShopMenuRepository extends JpaRepository<ShopMenu, Long> {

    List<ShopMenu> findAllByShop_Id(Long shopId);
}
