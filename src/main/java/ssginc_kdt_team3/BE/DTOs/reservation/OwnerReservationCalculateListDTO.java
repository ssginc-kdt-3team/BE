package ssginc_kdt_team3.BE.DTOs.reservation;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.Reservation;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OwnerReservationCalculateListDTO {

    private Long reservationId;
    private Long customerId;
    private int people;
    private String customerName;
    private int depositValue;
    private LocalDateTime reservationDate;

    @Builder
    public OwnerReservationCalculateListDTO(Reservation reservation) {
        this.reservationId = reservation.getId();
        this.customerId = reservation.getCustomer().getId();
        this.people = reservation.getPeople();
        this.customerName = reservation.getCustomer().getName();
        this.reservationDate = reservation.getReservationDate();
    }
}
