package ssginc_kdt_team3.BE.DTOs.reservation;

import lombok.Data;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CustomerReservationListDTO {
    private String shopName;
    private LocalDate expectedDate;
    private LocalTime expectedTime;
    private int people;
    private int child;
    private ReservationStatus reservationStatus;

    public CustomerReservationListDTO(Reservation reservation) {
        this.shopName = reservation.getShop().getName();
        this.expectedDate = reservation.getReservationDate().toLocalDate();
        this.expectedTime = reservation.getReservationDate().toLocalTime();
        this.people = reservation.getPeople();
        this.child = reservation.getChild();
        this.reservationStatus = reservation.getStatus();
    }
}
