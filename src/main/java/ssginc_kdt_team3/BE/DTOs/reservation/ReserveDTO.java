package ssginc_kdt_team3.BE.DTOs.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ReserveDTO { //점주(Owner)의 예약내역 확인용 DTO

  // 예약회원 정보: 로그인 한 회원 기준 -> Customer에서 가져오기
  private String name;
  private String phoneNumber;
  private String email;

  // 예약정보
  private Long id;
  private int people;
  private int child;
  private LocalDateTime reservationDate;
  private ReservationStatus status;
  private String memo;


  // 생성자 -> Service 코드에서 사용
  public ReserveDTO(Reservation reserve) {
    this.id = reserve.getId();
    this.name = reserve.getCustomer().getName();
    this.phoneNumber = reserve.getCustomer().getPhoneNumber();
    this.email = reserve.getCustomer().getEmail();
    this.people = reserve.getPeople();
    this.child = reserve.getChild();
    this.reservationDate = reserve.getReservationDate();
    this.status = reserve.getStatus();
    this.memo = reserve.getMemo();
  }

  // 필드 추가나 삭제시 생성자를 건들이는데, DTO는 변경이 많은데
  // 엔티티 필드값이랑 일치하는 값 가져오기 위해 모델매퍼 사용해(라이브러리)
  // 빌더: 생성자 만들필요 없어 -> 그럼 dto 안 걸들여도 되잖아
  // 모델매퍼, 빌더 사용법 알고

}