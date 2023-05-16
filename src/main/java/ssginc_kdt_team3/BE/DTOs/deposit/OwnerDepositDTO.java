package ssginc_kdt_team3.BE.DTOs.deposit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.enums.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class OwnerDepositDTO {
    private Long reservationId;
    private Long customerId;
    private String customerName;
    private String phoneNumber;
    private int originDeposit; //할인 전 금액

    // 방문일(예약일)
    private LocalDate expectedDay;
    private LocalTime expectedTime;
    private ReservationStatus reservationStatus;
    private DepositStatus status;

    public OwnerDepositDTO(Deposit deposit) {
        this.reservationId = deposit.getId();
        this.customerId = deposit.getReservation().getCustomer().getId();
        this.customerName = deposit.getReservation().getCustomer().getName();
        this.phoneNumber = deposit.getReservation().getCustomer().getPhoneNumber();
        this.originDeposit = deposit.getOrigin_value();
        this.expectedDay = deposit.getReservation().getReservationDate().toLocalDate();
        this.expectedTime = deposit.getReservation().getReservationDate().toLocalTime();
        this.reservationStatus = deposit.getReservation().getStatus();
        this.status = deposit.getStatus();
    }

}
