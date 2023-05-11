package ssginc_kdt_team3.BE.DTOs.reservation;

import lombok.Data;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;

import java.time.LocalDateTime;

@Data
public class ReserveTimeDTO {
  // 예약회원 정보
  private String name;
  private String phoneNumber;
  private String email;

  // 예약정보
  private int people;
  private int child;
  private LocalDateTime reservationDate;
  private ReservationStatus status;


  // 생성자 -> Service 코드에서 사용
  public ReserveTimeDTO(Reservation reserve) {
    this.name = reserve.getCustomer().getName();
    this.phoneNumber = reserve.getCustomer().getPhoneNumber();
    this.email = reserve.getCustomer().getEmail();
    this.people = reserve.getPeople();
    this.child = reserve.getChild();
    this.reservationDate = reserve.getReservationDate();
    this.status = reserve.getStatus();
  }

}

