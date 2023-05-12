package ssginc_kdt_team3.BE.DTOs.reservation;

import lombok.Data;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.util.TimeUtils;

@Data
public class OwnerReservationDetailDTO {
  //회원가입 한 회원 기준 -> 회원정보를 DTO로 받아 올 필요x
  private Long reservationId;

  // 예약회원 정보
  private String name;
  private String phoneNumber;
  private String email;

  // 예약정보
  private int people;
  private int child;
  private String reservationDate;
  private ReservationStatus status;
  private String memo;
  private String cancelReason;

  private int deposit;

  public OwnerReservationDetailDTO(Reservation reservation, Deposit deposit) {
    this.reservationId = reservation.getId();
    this.name = reservation.getCustomer().getName();
    this.phoneNumber = reservation.getCustomer().getPhoneNumber();
    this.email = reservation.getCustomer().getEmail();
    this.people = reservation.getPeople();
    this.child = reservation.getChild();
    this.reservationDate = TimeUtils.localDataTimeParseString(reservation.getReservationDate());
    this.status = reservation.getStatus();
    this.memo = reservation.getMemo();
    this.cancelReason = reservation.getCancelReason();
    this.deposit = deposit.getOrigin_value();
  }
}

