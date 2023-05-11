package ssginc_kdt_team3.BE.DTOs.reservation;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.util.TimeUtils;

@Data
@NoArgsConstructor
public class OwnerReservationFilterListDTO {
    private Long reservationId;

    // 예약회원 정보
    private Long customerId;
    private String customerName;
    private String customerPhoneNumber;

    // 예약정보
    private String reservationDate;
    private int people;
    private ReservationStatus status;

    private int deposit;
    private int penalty;

    public OwnerReservationFilterListDTO(Reservation reservation, Deposit deposit) {
        this.reservationId = reservation.getId();
        this.customerId = reservation.getCustomer().getId();
        this.customerName = reservation.getCustomer().getName();
        this.customerPhoneNumber = reservation.getCustomer().getPhoneNumber();
        this.reservationDate = TimeUtils.localDataTimeParseString(reservation.getReservationDate());
        this.people = reservation.getPeople();
        this.status = reservation.getStatus();
        this.deposit = deposit.getOrigin_value();
        this.penalty = deposit.getPenaltyValue();
    }
}
