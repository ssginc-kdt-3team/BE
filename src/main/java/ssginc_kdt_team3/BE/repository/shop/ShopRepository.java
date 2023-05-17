//package ssginc_kdt_team3.BE.repository.shop;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Repository;
//import ssginc_kdt_team3.BE.DTOs.shop.ShopDetailDTO;
//import javax.persistence.EntityManager;
//import java.util.List;
//
//@Slf4j
//@RequiredArgsConstructor
//@Repository
//public class ShopRepository {
//
//    private final EntityManager em;
//
//    public List<ShopDetailDTO> ShopDetailJoin(){
//        log.info("log info = {}",1111111);
//        List<ShopDetailDTO> SimList  = em.createQuery("SELECT s FROM Shop s " +
//                "JOIN s.ShopOperationInfo i " +
//                "JOIN s.ShopMenu m",ShopDetailDTO.class)
//                .getResultList();
//        log.info("log info{}=",22222);
//        return SimList;
//    }
//
//}
//
