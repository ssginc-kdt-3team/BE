package ssginc_kdt_team3.BE.DTOs.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.util.TimeUtils;

import java.time.LocalDateTime;

@Data
public class OwnerReservationDTO { //점주(Owner)의 예약내역 확인용 DTO

  private Long id;
  private Long ownerID;
  //0517 임태경 고객 id 추가
  private Long customerID;

  // 예약회원 정보: 로그인 한 회원 기준 -> Customer에서 가져오기
  private String name;
  private String phoneNumber;
  private String email;

  // 예약정보
  private int people;
  private int child;
  private LocalDateTime reservationDate;
  private ReservationStatus status;
  private String memo;
  private int originValue; //예약금
  private int penaltyValue; //위약금


  // 생성자 -> Service 코드에서 사용
  public OwnerReservationDTO(Reservation reserve, Deposit deposit) {
    this.id = reserve.getId();
    this.customerID = reserve.getCustomer().getId();
    this.ownerID = reserve.getShop().getOwner().getId();

    this.name = reserve.getCustomer().getName();
    this.phoneNumber = reserve.getCustomer().getPhoneNumber();
    this.email = reserve.getCustomer().getEmail();
    this.people = reserve.getPeople();

    //상세
    this.child = reserve.getChild();
    this.reservationDate = reserve.getReservationDate();
    this.status = reserve.getStatus();
    //상세
    this.memo = reserve.getMemo();

    this.originValue = deposit.getOrigin_value();
    this.penaltyValue = deposit.getPenaltyValue();
  }
}