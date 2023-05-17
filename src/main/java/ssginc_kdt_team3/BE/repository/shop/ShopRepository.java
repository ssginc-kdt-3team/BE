package ssginc_kdt_team3.BE.repository.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssginc_kdt_team3.BE.DTOs.shop.ShopDetailDTO;
import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ShopRepository {

    private final EntityManager em;

    public List<ShopDetailDTO> ShopDetailJoin(){

        List<ShopDetailDTO> SimList  = em.createQuery("SELECT s,i,m FROM Shop s " +
                "JOIN s.ShopOperation i " +
                "JOIN s.ShopMenu m",ShopDetailDTO.class)
                .getResultList();
        return SimList;
    }

}

