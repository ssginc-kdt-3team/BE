package ssginc_kdt_team3.BE.DTOs.reservation;

import lombok.Data;
import ssginc_kdt_team3.BE.domain.Reservation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class
CustomerReservationUpdateDTO {

    private Long reservationId;
    private String branchName;
    private String shopName;
    private String reservationDateTime;
    private int people;
    private int child;
    private String memo;

    public CustomerReservationUpdateDTO() {
    }

    public CustomerReservationUpdateDTO(Reservation reservation) {
        String time = reservation.getReservationDate().toString().replace("T", " ").concat(":00");
//        System.out.println("time ==================================================================" + s);

        this.reservationId = reservation.getId();
        this.branchName = reservation.getShop().getBranch().getName();
        this.shopName = reservation.getShop().getName();
        this.reservationDateTime = time;
        this.people = reservation.getPeople();
        this.child = reservation.getChild();
        this.memo = reservation.getMemo();
    }
}
