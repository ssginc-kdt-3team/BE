package ssginc_kdt_team3.BE.service.owner.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.owner.reserve.ReserveDTO;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.repository.owner.OwnerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerReserveService {
  private final OwnerRepository ownerRepository;

  // 매장 모든 예약내역 조회
  public List<Reservation> getAllReserve(){
    List<Reservation> allReserve = ownerRepository.findAllReserve();
    return allReserve;
  }

  // 당일 예약 시간별 조회

}
