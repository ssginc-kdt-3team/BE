package ssginc_kdt_team3.BE.service.owner.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.reservation.OwnerReservationDTO;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.reservation.OwnerRepository;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;

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
  private final JpaDataReservationRepository reservationRepository;

  // 매장 모든 예약내역 조회: 페이징처리
  public Page<OwnerReservationDTO> getAllReserve(Pageable pageable) {
//    List<Reservation> allReserve = ownerRepository.findAllReserve();

    Page<Reservation> all = reservationRepository.findAll(pageable);
    System.out.println("all = " + all);
    return toDtoPage(all);
  }

  private Page<OwnerReservationDTO> toDtoPage(Page<Reservation> reservationList) {
    Page<OwnerReservationDTO> reserveDtoList = reservationList.map( //빌더패턴
        m -> OwnerReservationDTO.builder()
            .id(m.getId())
            .name(m.getCustomer().getName())
            .phoneNumber(m.getCustomer().getPhoneNumber())
            .build());
    return reserveDtoList;
  }

  // 활성화된 예약 조회: ReservationStatus가 RESERVATION인 경우
  public List<OwnerReservationDTO> getActiveReserve() {
    List<Reservation> byStatus = ownerRepository.findByStatus(ReservationStatus.RESERVATION);

    List<OwnerReservationDTO> reserveList = new ArrayList<>();
    for (Reservation list : byStatus) {
      OwnerReservationDTO dto = new OwnerReservationDTO(list);
      reserveList.add(dto);
    }
    return reserveList;
  }

  // 당일 예약 시간별 조회
  public List<OwnerReservationDTO> getReserveTime(String type) {

    log.info("service");
    LocalDateTime now = LocalDateTime.now();



    // == 이건 같은 변수가 저장되는 메모리를 비교, 둘이 저장된 곳이 다르면 안 돼
    // equals는 주소는 상관없고 실제값만 비교하기 때문

    if (type.equals("A")) {
      List<Reservation> after1h = ownerRepository.findDateBetween(LocalDateTime.now(), LocalDateTime.now().plusHours(1));

      return listToDto(after1h);

    } else if (type.equals("B")) {
      log.info("service B");
      List<Reservation> after3h = ownerRepository.findDateBetween(LocalDateTime.now(), LocalDateTime.now().plusHours(3));

      return listToDto(after3h);

    } else if (type.equals("C")) {
      LocalDateTime startLunch = now.with(LocalTime.of(11, 0));
      List<Reservation> betweenLunch = ownerRepository.findDateBetween(startLunch, startLunch.plusHours(2));

      return listToDto(betweenLunch);

    } else if (type.equals("D")) {
      LocalDateTime startDinner = now.with(LocalTime.of(17, 0));
      List<Reservation> betweenDinner = ownerRepository.findDateBetween(startDinner, startDinner.plusHours(2));

      return listToDto(betweenDinner);
    }
    return null;
  }

  private static List<OwnerReservationDTO> listToDto(List<Reservation> b) { // 반복문 처리: Ctrl+Alt+M
    List<OwnerReservationDTO> reserveList = new ArrayList<>();

    for (Reservation res : b) {
      OwnerReservationDTO dto = new OwnerReservationDTO(res);
      reserveList.add(dto);
    }
    return reserveList;
  }

}