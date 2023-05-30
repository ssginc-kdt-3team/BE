package ssginc_kdt_team3.BE.DTOs.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ReservationAlarmDTO {

    private Long reservationId;
    private ReservationStatus reservationStatus;
    private LocalDateTime reservationDate;
    private LocalDateTime applyTime;
    private LocalDateTime changeTime;
    private Long customerId;
    private String customerName;
    private String phoneNumber;


}
