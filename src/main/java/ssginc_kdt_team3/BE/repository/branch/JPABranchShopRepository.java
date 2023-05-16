package ssginc_kdt_team3.BE.repository.branch;

import org.springframework.data.jpa.repository.JpaRepository;
import ssginc_kdt_team3.BE.domain.Shop;

import java.util.List;
import java.util.Optional;

public interface JPABranchShopRepository extends JpaRepository<Shop, Long> {

    Optional<Shop> findById(long id);

    List<Shop> findByBranchId(long id);
}

