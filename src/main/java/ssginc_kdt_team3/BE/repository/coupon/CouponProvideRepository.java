package ssginc_kdt_team3.BE.repository.coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import ssginc_kdt_team3.BE.domain.Coupon;
import ssginc_kdt_team3.BE.domain.CouponProvide;
import ssginc_kdt_team3.BE.enums.CouponStatus;

import java.util.List;

public interface CouponProvideRepository extends JpaRepository<CouponProvide, Long> {

  List<CouponProvide> findAllByStatusAndCustomer_IdOrderByGivenDay(CouponStatus status, Long userId);



}
