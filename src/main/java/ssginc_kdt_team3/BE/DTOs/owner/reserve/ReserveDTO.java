package ssginc_kdt_team3.BE.DTOs.owner.reserve;

import lombok.AllArgsConstructor;
import lombok.Data;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.enums.ReservationStatus;

import java.time.LocalDateTime;

@Data
public class ReserveDTO { //회원가입 한 회원 기준 -> 회원정보를 DTO로 받아 올 필요x
  private int people;
  private int child;
  private LocalDateTime reservationDate;
  private ReservationStatus status;
  private String memo;

}

