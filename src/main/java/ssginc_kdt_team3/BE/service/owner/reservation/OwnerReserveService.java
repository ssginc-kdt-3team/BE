package ssginc_kdt_team3.BE.service.owner.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.reservation.OwnerReserveDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.ReserveTimeDTO;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.owner.reservation.OwnerRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerReserveService {
  private final OwnerRepository ownerRepository;

  // 매장 모든 예약내역 조회
  public List<OwnerReserveDTO> getAllReserve(){
    List<Reservation> allReserve = ownerRepository.findAllReserve();

    List<OwnerReserveDTO> ownerReserveDTOList = new ArrayList<OwnerReserveDTO>();
    for (Reservation list : allReserve) {
      OwnerReserveDTO ownerReserveDTO = new OwnerReserveDTO(list);
      ownerReserveDTOList.add(ownerReserveDTO);
    }
    return ownerReserveDTOList;
  }

  // 당일 예약 시간별 조회
  public List<ReserveTimeDTO> getReserveTime(){
    List<Reservation> allReserve = ownerRepository.findAllReserve();

      List<ReserveTimeDTO> reserveTimeList = new ArrayList<>();

      for (Reservation list : allReserve) {

        if(list.getStatus().equals(ReservationStatus.RESERVATION)) {
          ReserveTimeDTO reserveTime = new ReserveTimeDTO(list);
          reserveTimeList.add(reserveTime);
        }
        return reserveTimeList;
      }
      return null;
  }

}
