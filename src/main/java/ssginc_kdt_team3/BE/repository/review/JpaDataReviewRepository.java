package ssginc_kdt_team3.BE.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.domain.Review;

import java.util.Optional;

public interface JpaDataReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByReservationId(Long reservationId);
}
