package ssginc_kdt_team3.BE.DTOs.deposit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.enums.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class OwnerDepositDTO {
    private Long reservationId;
    private Long customerId;
    private String customerName;
    private String phoneNumber;
    private int people;
    private int child;
    private int originDeposit; //할인 전 예약금
    private int penaltyValue; // 위약금
    private LocalDateTime reservationDate; // 방문일(예약일)
    private ReservationStatus reservationStatus;
    private DepositStatus depositStatus;

    public OwnerDepositDTO(Deposit deposit) {
        this.reservationId = deposit.getId();
        this.customerId = deposit.getReservation().getCustomer().getId();
        this.customerName = deposit.getReservation().getCustomer().getName();
        this.phoneNumber = deposit.getReservation().getCustomer().getPhoneNumber();
        this.people = deposit.getReservation().getPeople();
        this.child = deposit.getReservation().getChild();
        this.originDeposit = deposit.getOrigin_value();
        this.penaltyValue = deposit.getPenaltyValue();
        this.reservationDate = deposit.getReservation().getReservationDate();
        this.reservationStatus = deposit.getReservation().getStatus();
        this.depositStatus = deposit.getStatus();
    }

}
