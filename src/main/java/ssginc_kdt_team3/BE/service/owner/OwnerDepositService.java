package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.deposit.OwnerDepositDTO;
import ssginc_kdt_team3.BE.DTOs.deposit.OwnerMainDepositDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerReviewListDTO;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Review;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.repository.deposit.DepositRepository;
import ssginc_kdt_team3.BE.repository.shop.JpaDataShopRepository;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OwnerDepositService {
  private final DepositRepository depositRepository;
  private final JpaDataShopRepository shopRepository;

  public Page<OwnerDepositDTO> getDeposit(Long ownerId, Pageable pageable,String status, int year, int month){

    Shop shop = shopRepository.findShopByOwner_id(ownerId).get();

    YearMonth yearMonth = YearMonth.of(year, month);
    int lastDayOfMonth = yearMonth.lengthOfMonth();

    // 기본 페이지: 해당 달의 전체 예약금 목록
    LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0, 0);
    LocalDateTime end = LocalDateTime.of(year, month, lastDayOfMonth, 23, 59, 59);


    if (status.equals("ALL")) {
      Page<Deposit> pages = depositRepository.findDepositByReservation_Shop_IdAndReservation_ReservationDateBetween(shop.getId(), start, end, pageable);
      return convertDto(pages);

      } else if(status.equals("RECEIVE")) {
      Page<Deposit> pages = depositRepository.findDepositByReservation_Shop_IdAndStatusAndReservation_ReservationDateBetween(shop.getId(), DepositStatus.RECEIVE, start, end, pageable);
      return convertDto(pages);

    } else if(status.equals("RETURN")) {
      Page<Deposit> pages = depositRepository.findDepositByReservation_Shop_IdAndStatusAndReservation_ReservationDateBetween(shop.getId(), DepositStatus.RECEIVE, start, end, pageable);
      return convertDto(pages);

    } else if(status.equals("HALF_PENALTY")) {
      Page<Deposit> pages = depositRepository.findDepositByReservation_Shop_IdAndStatusAndReservation_ReservationDateBetween(shop.getId(), DepositStatus.RECEIVE, start, end, pageable);
      return convertDto(pages);

    } else if(status.equals("ALL_PENALTY")) {
      Page<Deposit> pages = depositRepository.findDepositByReservation_Shop_IdAndStatusAndReservation_ReservationDateBetween(shop.getId(), DepositStatus.RECEIVE, start, end, pageable);
      return convertDto(pages);
    }
    return null;

  }

  public Integer showMonthTotalPenalty(Long ownerId, Map<String, String> request) {

    LocalDateTime start = null;
    LocalDateTime end = null;
    try {
      String years = request.get("year");
      String months = request.get("month");

      int year = Integer.parseInt(years);
      int month = Integer.parseInt(months);

      YearMonth yearMonth = YearMonth.of(year, month);
      int lastDayOfMonth = yearMonth.lengthOfMonth();

      // 기본 페이지: 해당 달의 전체 예약금 목록
      start = LocalDateTime.of(year, month, 1, 0, 0, 0);
      end = LocalDateTime.of(year, month, lastDayOfMonth, 23, 59, 59);

    } catch (Exception e) {
      return -9999;
    }

    Optional<Shop> shopByOwnerId = shopRepository.findShopByOwner_id(ownerId);

    if (shopByOwnerId.isPresent()) {
      Shop shop = shopByOwnerId.get();
      return depositRepository.findMonthlyPenalty(shop.getId(), start, end);

    } else {
      return -9998;
    }


  }

  public OwnerMainDepositDTO showMonthlyDeposit(Long ownerId) {

    Optional<Shop> shopByOwnerId = shopRepository.findShopByOwner_id(ownerId);

    if (shopByOwnerId.isPresent()) {
      Shop shop = shopByOwnerId.get();
      LocalDateTime now = LocalDateTime.now();
      LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth())
              .with(LocalTime.MIN);
      LocalDateTime endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth())
              .with(LocalTime.MAX);

      return getOwnerMainDepositDTO(shop, startOfMonth, endOfMonth);
    }

    return null;
  }

  private OwnerMainDepositDTO getOwnerMainDepositDTO(Shop shop, LocalDateTime startOfMonth, LocalDateTime endOfMonth) {
    int last3MonthlyPenalty = depositRepository.findMonthlyPenalty(shop.getId(), startOfMonth.minusMonths(3), endOfMonth.minusMonths(3));
    int last2MonthlyPenalty = depositRepository.findMonthlyPenalty(shop.getId(), startOfMonth.minusMonths(2), endOfMonth.minusMonths(2));
    int lastMonthlyPenalty = depositRepository.findMonthlyPenalty(shop.getId(), startOfMonth.minusMonths(1), endOfMonth.minusMonths(1));
    int monthlyPenalty = depositRepository.findMonthlyPenalty(shop.getId(), startOfMonth, endOfMonth);
    OwnerMainDepositDTO ownerMainDepositDTO = new OwnerMainDepositDTO(last3MonthlyPenalty, last2MonthlyPenalty, lastMonthlyPenalty, monthlyPenalty);
    return ownerMainDepositDTO;
  }

  // Page로 받은 Review 엔티티를 DTO로 변환
  private Page<OwnerDepositDTO> convertDto(Page<Deposit> deposits) {
    List<OwnerDepositDTO> depositDTOList = new ArrayList<>();

    for(Deposit d : deposits){
      OwnerDepositDTO depositDTO = new OwnerDepositDTO(d);
      depositDTOList.add(depositDTO);
    }
    return new PageImpl<>(depositDTOList, deposits.getPageable(), deposits.getTotalElements());
  }

}
