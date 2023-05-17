package ssginc_kdt_team3.BE.service.owner.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.reservation.OwnerReservationDTO;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.repository.deposit.DepositRepository;
import ssginc_kdt_team3.BE.repository.reservation.OwnerRepository;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;
import ssginc_kdt_team3.BE.repository.shop.JpaDataShopRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerReserveService {
  private final OwnerRepository ownerRepository;
  private final JpaDataReservationRepository jpaDataReservationRepository;
  private final DepositRepository depositRepository;
  private final JpaDataShopRepository shopRepository;

  // 매장 모든 예약내역 조회: 페이징처리
  public Page<OwnerReservationDTO> getAllReserve(Pageable pageable, Long ownerId) {
    Shop shop = shopRepository.findShopByOwner_id(ownerId).get();
    List<Reservation> allByShopId = jpaDataReservationRepository.findAllByShop_Id(shop.getId());
    System.out.println("all = " + allByShopId);
    return toDtoPage(allByShopId, pageable);
  }

  // List로 Reservation 받아와서 -> dto -> 페이지로
  private Page<OwnerReservationDTO> toDtoPage(List<Reservation> reservationList, Pageable pageable) {
    List<OwnerReservationDTO> dtoList = new ArrayList<>();

    for (Reservation res : reservationList) {
      Deposit reservationDeposit = depositRepository.findReservationDeposit(res.getId());
      OwnerReservationDTO dto = new OwnerReservationDTO(res, reservationDeposit);
      dtoList.add(dto);
    }

    // dto -> Page로
    final int start = (int) pageable.getOffset();
    final int end = Math.min((start + pageable.getPageSize()), dtoList.size());

        Page<OwnerReservationDTO> ownerPage = new PageImpl<>(dtoList.subList(start, end), pageable, dtoList.size());

    return ownerPage;
  }

  // 활성화된 예약 조회: ReservationStatus가 RESERVATION인 경우
  // List로 Reservation 받아와서 -> dto -> 페이지로
  public Page<OwnerReservationDTO> getActiveReserve(Pageable pageable, Long ownerId) {
    Shop shop = shopRepository.findShopByOwner_id(ownerId).get();
//    List<Reservation> allByShopId = jpaDataReservationRepository.findAllByShop_Id(shop.getId()); 이건 shopId가 같은 예약정보 리스트야

    List<Reservation> byStatus = ownerRepository.findByStatus(shop.getId());

    List<OwnerReservationDTO> dtoList = new ArrayList<>();
    for (Reservation list : byStatus) {
      log.info("없는 아이디는 몇번일까 ============ {}", list.getId());
      Deposit reservationDeposit = depositRepository.findReservationDeposit(list.getId());
      OwnerReservationDTO dto = new OwnerReservationDTO(list, reservationDeposit);
      dtoList.add(dto);
    }
    // dto -> Page로
    final int start = (int) pageable.getOffset();
    final int end = Math.min((start + pageable.getPageSize()), dtoList.size());

    Page<OwnerReservationDTO> activePage = new PageImpl<>(dtoList.subList(start, end), pageable, dtoList.size());
    return activePage;
  }



  // 당일 예약 시간별 조회
  public Page<OwnerReservationDTO> getReserveTime(String type, Long ownerId, Pageable pageable) {
    // shopId 찾아와서 내 매장의 예약시간만 가져오고 싶어 -> shop.getId();
    Shop shop = shopRepository.findShopByOwner_id(ownerId).get();

    LocalDateTime now = LocalDateTime.now();

    if (type.equals("A")) { // 1시간 후
      List<Reservation> after1h = ownerRepository.findDateBetween(now, now.plusHours(1), shop.getId());
      return listToDto(after1h, pageable);

    } else if (type.equals("B")) { // 3시간 후
      log.info("service B");
      List<Reservation> after3h = ownerRepository.findDateBetween(now, now.plusHours(3), shop.getId());
      return listToDto(after3h, pageable);

    } else if (type.equals("C")) { // 점심시간(11~1)
      LocalDateTime startLunch = now.with(LocalTime.of(11, 0));
      List<Reservation> betweenLunch = ownerRepository.findDateBetween(startLunch, startLunch.plusHours(2), shop.getId());
      return listToDto(betweenLunch, pageable);

    } else if (type.equals("D")) { // 저녁시간(17~19)
      LocalDateTime startDinner = now.with(LocalTime.of(17, 0));
      List<Reservation> betweenDinner = ownerRepository.findDateBetween(startDinner, startDinner.plusHours(2), shop.getId());
      return listToDto(betweenDinner, pageable);

    } else if (type.equals("E")) { // 전체 시간
      List<Reservation> allTime = ownerRepository.findDateBetween(now.with(LocalTime.of(12, 0)), now.with(LocalTime.of(24, 0)), shop.getId());
      return listToDto(allTime, pageable);
    }
    return null;
  }

  // Reservation -> DTO 변경 반복문처리
  private Page<OwnerReservationDTO> listToDto(List<Reservation> b, Pageable pageable) {
    List<OwnerReservationDTO> reserveList = new ArrayList<>();

    for (Reservation res : b) {
      Deposit reservationDeposit = depositRepository.findReservationDeposit(res.getId());
      OwnerReservationDTO dto = new OwnerReservationDTO(res, reservationDeposit);
      reserveList.add(dto);
    }
    // dto -> Page로
    final int start = (int) pageable.getOffset();
    final int end = Math.min((start + pageable.getPageSize()), reserveList.size());

    Page<OwnerReservationDTO> activePage = new PageImpl<>(reserveList.subList(start, end), pageable, reserveList.size());
    return activePage;
  }

}