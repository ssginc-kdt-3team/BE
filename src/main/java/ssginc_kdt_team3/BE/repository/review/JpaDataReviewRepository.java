package ssginc_kdt_team3.BE.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.domain.Review;

import java.util.List;
import java.util.Optional;

public interface JpaDataReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByReservationId(Long reservationId);

//    @Query(value = "select r from Review r Shop s, s.reservation r2, r2.review as r where r2.shop.id = :shopId and r2.id = r.reservation.id", nativeQuery = true)
////    @Query("SELECT r FROM Review r INNER JOIN WHERE r.reservation.id = :reservationId ")
//    List<Review> findReviewByShopId(@Param(value = "shopId") Long shopId);

    List<Review> findAllByReservation_ShopId(@Param(value = "shopId") Long shopId);
}
