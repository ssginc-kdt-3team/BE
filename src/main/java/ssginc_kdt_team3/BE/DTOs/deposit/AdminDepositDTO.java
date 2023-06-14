package ssginc_kdt_team3.BE.DTOs.deposit;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.enums.DepositStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class AdminDepositDTO {

    private Long reservationId;

    private Long customerId;

    private String customerName;

    private int originDeposit;

    private int payDeposit;

    private int penalty;

    private LocalDate expectedDay;

    private LocalTime expectedTime;

    private DepositStatus status;

    @Builder
    public AdminDepositDTO(Long reservationId, Long customerId, String customerName, int originDeposit, int payDeposit, int penalty, LocalDate expectedDay, LocalTime expectedTime, DepositStatus status) {
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.originDeposit = originDeposit;
        this.payDeposit = payDeposit;
        this.penalty = penalty;
        this.expectedDay = expectedDay;
        this.expectedTime = expectedTime;
        this.status = status;
    }

    public AdminDepositDTO(Deposit deposit) {
        this.reservationId = deposit.getId();
        this.customerId = deposit.getReservation().getCustomer().getId();
        this.customerName = deposit.getReservation().getCustomer().getName();
        this.originDeposit = deposit.getOrigin_value();
        this.payDeposit = deposit.getPayValue();
        this.penalty = deposit.getPenaltyValue();
        this.expectedDay = deposit.getReservation().getReservationDate().toLocalDate();
        this.expectedTime = deposit.getReservation().getReservationDate().toLocalTime();
        this.status = deposit.getStatus();
    }
}
