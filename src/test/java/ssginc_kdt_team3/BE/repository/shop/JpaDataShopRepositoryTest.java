package ssginc_kdt_team3.BE.repository.shop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.domain.Shop;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JpaDataShopRepositoryTest {

    @Autowired
    JpaDataShopRepository repository;

    @Test
    void findShopByOwner_id() {
        Long ownerId = 1L;

        Optional<Shop> shopByOwnerId = repository.findShopByOwner_id(ownerId);
        Assertions.assertThat(shopByOwnerId.get().getOwner().getId()).isEqualTo(ownerId);
    }
}