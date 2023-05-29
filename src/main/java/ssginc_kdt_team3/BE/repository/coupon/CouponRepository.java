package ssginc_kdt_team3.BE.repository.coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import ssginc_kdt_team3.BE.domain.Coupon;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {



}
