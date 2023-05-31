package ssginc_kdt_team3.BE.service.admin;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.coupon.CreateCouponDTO;
import ssginc_kdt_team3.BE.domain.Admin;
import ssginc_kdt_team3.BE.domain.Coupon;
import ssginc_kdt_team3.BE.domain.CouponManage;
import ssginc_kdt_team3.BE.enums.CouponManageType;
import ssginc_kdt_team3.BE.repository.admin.JpaDateAdminRepository;
import ssginc_kdt_team3.BE.repository.coupon.CouponManageRepository;
import ssginc_kdt_team3.BE.repository.coupon.CouponRepository;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponManagementService {
  private final CouponRepository couponRepository;
  private final JpaDateAdminRepository adminRepository;
  private final CouponManageRepository manageRepository;

  // 쿠폰 생성
  public Coupon createCoupon(CreateCouponDTO couponDTO, Long adminId) {

    Optional<Admin> adminRepositoryById = adminRepository.findById(adminId);
    if(adminRepositoryById.isPresent()) {
      Coupon coupon = new Coupon();
      coupon.setCouponName(couponDTO.getCouponName());
      coupon.setType(couponDTO.getType()); // 1000원, 2000원 권
      coupon.setDiscountValue(couponDTO.getDiscountValue());
      coupon.setDiscountRate(0);
      coupon.setCreateDate(LocalDateTime.now());
      coupon.setUpdateDate(LocalDateTime.now());

      Coupon saveCoupon = couponRepository.save(coupon);

      // 저장하자 마자 기록되야 하니까 -> manageCoupon 호출
      manageCoupon(saveCoupon, adminRepositoryById.get(), CouponManageType.CREATE);
      return saveCoupon;

    }
    //관리자 아이디 존재하지 않는 경우
    return null;
  }

  // 쿠폰을 생성했다는 기록 생성
  public void manageCoupon(Coupon coupon, Admin admin, CouponManageType manageType) {
    // 관리자랑 생성된 쿠폰id 받아와서 수정, 삭제 등 변경내역 기록

    // couponManage를 저장해야 돼
    CouponManage couponManage = new CouponManage();
    couponManage.setManageType(manageType);
    couponManage.setCreateDate(coupon.getCreateDate().toLocalDate());
    couponManage.setUpdateDate(LocalDate.now());
    couponManage.setCoupon(coupon);
    couponManage.setAdmin(admin);

    manageRepository.save(couponManage);
  }

  // 쿠폰번호 생성
  public String createNumber() {
    String number = UUID.randomUUID().toString().replaceAll("-", "");
    String substring = number.substring(0, 9);
    System.out.println(substring);
    return substring;
  }
}
