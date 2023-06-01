package ssginc_kdt_team3.BE.service.coupon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.reservation.CustomerReservationAddDTO;
import ssginc_kdt_team3.BE.domain.Coupon;
import ssginc_kdt_team3.BE.domain.CouponProvide;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.enums.CouponStatus;
import ssginc_kdt_team3.BE.repository.coupon.CouponProvideRepository;
import ssginc_kdt_team3.BE.repository.coupon.CouponRepository;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CouponProvideService {

    private final CouponRepository couponRepository;
    private final CouponProvideRepository couponProvideRepository;
    private final CouponManagementService couponManagementService;

    public int getCouponDiscount(CustomerReservationAddDTO dto, int originValue) {
        int result = 0;
        if (dto.getCouponId() > 0) {
            Optional<CouponProvide> byId = couponProvideRepository.findById(dto.getCouponId());

            if (byId.isPresent()) {
                CouponProvide couponProvide = byId.get();

                //쿠폰 유효성 검사
                if (couponProvide.getOutDay().isAfter(LocalDate.now()) && couponProvide.getStatus().equals(CouponStatus.GIVEN)) {
                    Coupon coupon = couponProvide.getCoupon();
                    if (coupon.getType().equals("금액할인") && coupon.getDiscountValue() > 0) {
                        result += coupon.getDiscountValue();
                    } else if (coupon.getType().equals("금액할인") && coupon.getDiscountRate() > 0) {
                        result += (int) Math.round(originValue * coupon.getDiscountRate());
                    }
                    return result;
                }
                return -1;
            }
            return -1;
        }
        return -1;
    }

    public boolean saveReservationCouponUseStatus(Deposit deposit, Long couponProvideId) {
        Optional<CouponProvide> byId = couponProvideRepository.findById(couponProvideId);
        if (byId.isPresent()) {
            CouponProvide couponProvide = byId.get();

            couponProvide.setStatus(CouponStatus.USE);
            couponProvide.setDeposit(deposit);
            couponProvide.setReason(deposit.getReservation().getId() + "번 예약에 사용");
            couponProvideRepository.save(couponProvide);

            return true;
        }
        return false;
    }
}
