package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.deposit.OwnerDepositDTO;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.deposit.DepositRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OwnerDepositService {
  private final DepositRepository depositRepository;

  public List<OwnerDepositDTO> getDeposit(Long shopId){

    List<Deposit> deposits = depositRepository.findShopDepositList(shopId);

    // 예약상태에 따른 DepositStatus 설정
    for (Deposit deposit : deposits) {
      ReservationStatus status = deposit.getReservation().getStatus();
      DepositStatus depositStatus = deposit.getStatus();

      if (status == ReservationStatus.RESERVATION || status == ReservationStatus.DONE) {
        depositStatus = DepositStatus.RECEIVE;
      } else if (status == ReservationStatus.CANCEL) {
        depositStatus = DepositStatus.RETURN;
      } else if (status == ReservationStatus.IMMINENT) {
        depositStatus = DepositStatus.HALF_PENALTY;
      } else if (status == ReservationStatus.NOSHOW) {
        depositStatus = DepositStatus.ALL_PENALTY;
      }
      deposit.setStatus(depositStatus);
      }

    return getDepositDto(deposits);
  }

  public static List<OwnerDepositDTO> getDepositDto(List<Deposit> deposits){
    List<OwnerDepositDTO> dtoList = new ArrayList<>();

    for (Deposit d : deposits) {
      OwnerDepositDTO dto = new OwnerDepositDTO(d);
      dtoList.add(dto);
    }
    return dtoList;
  }//레포지토리 정보를 가져오기만하면 된다고 ?
}
