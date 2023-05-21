package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.deposit.OwnerDepositDTO;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.repository.deposit.DepositRepository;
import ssginc_kdt_team3.BE.repository.shop.JpaDataShopRepository;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

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
      List<Deposit> defaultPage = depositRepository.findShopDepositListBetween(shop.getId(), start, end);
      return getDepositDto(defaultPage, pageable);

      } else if(status.equals("RECEIVE")) {
      List<Deposit> receive = depositRepository.findDepositByStatusBetween(shop.getId(),DepositStatus.RECEIVE, start, end);
      return getDepositDto(receive, pageable);

    } else if(status.equals("RETURN")) {
      List<Deposit> aReturn = depositRepository.findDepositByStatusBetween(shop.getId(),DepositStatus.RETURN, start, end);
      return getDepositDto(aReturn, pageable);

    } else if(status.equals("HALF_PENALTY")) {
      List<Deposit> halfPenalty = depositRepository.findDepositByStatusBetween(shop.getId(),DepositStatus.HALF_PENALTY, start, end);
      return getDepositDto(halfPenalty, pageable);

    } else if(status.equals("ALL_PENALTY")) {
      List<Deposit> allPenalty = depositRepository.findDepositByStatusBetween(shop.getId(),DepositStatus.ALL_PENALTY, start, end);
      return getDepositDto(allPenalty, pageable);
    }
    return null;

  }

  // List -> DTO
  public static Page<OwnerDepositDTO> getDepositDto(List<Deposit> deposits, Pageable pageable){
    log.info("페이지 처리");
    List<OwnerDepositDTO> dtoList = new ArrayList<>();
    for(Deposit d : deposits) {
      log.info("");
      OwnerDepositDTO dto = new OwnerDepositDTO(d);
      dtoList.add(dto);
    }

    // DTO -> PAGE 변경
    final int start = (int) pageable.getOffset();
    final int end = Math.min((start + pageable.getPageSize()), dtoList.size());

    Page<OwnerDepositDTO> depositPage = new PageImpl<>(dtoList.subList(start, end), pageable, dtoList.size());

    return depositPage;
  }
}
