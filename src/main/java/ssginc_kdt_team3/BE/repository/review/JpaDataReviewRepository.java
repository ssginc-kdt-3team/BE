package ssginc_kdt_team3.BE.repository.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssginc_kdt_team3.BE.DTOs.admin.AdminReviewListDTO;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.domain.Review;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JpaDataReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByReservationId(Long reservationId);

//    @Query(value = "select r from Review r Shop s, s.reservation r2, r2.review as r where r2.shop.id = :shopId and r2.id = r.reservation.id", nativeQuery = true)
////    @Query("SELECT r FROM Review r INNER JOIN WHERE r.reservation.id = :reservationId ")
//    List<Review> findReviewByShopId(@Param(value = "shopId") Long shopId);

    List<Review> findAllByReservation_ShopId(@Param(value = "shopId") Long shopId);


    /**
     * 0524 이현: 관리자 후기목록 조회기능에 사용, AdminReviewService
     * */

    // 1. 지점, 매장 상관 없이 전체 조회
    Page<Review> findAllByReservation_Shop_Branch_Id(Long branchId, Pageable pageable);

    // 2. 지점선택: 해당 지점의 모든 매장들 후기
    Page<Review> findAllByReservation_Shop_Id(Long shopId, Pageable pageable);

    // 3. 매장선택: 해당 매장의 후기목록 조회

}
