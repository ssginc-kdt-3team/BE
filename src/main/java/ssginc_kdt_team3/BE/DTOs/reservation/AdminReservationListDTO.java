package ssginc_kdt_team3.BE.DTOs.reservation;

import lombok.Data;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.util.TimeUtils;

@Data
public class AdminReservationListDTO {

    private Long shopId;
    private Long branchId;
    private Long customerId;
    private Long reservationId;

    private String reservationTime;
    private int people;
    private ReservationStatus status;

    private String customerName;
    private String customerPhone;

    private String branchName;
    private String shopName;

    public AdminReservationListDTO(Reservation reservation) {
        this.reservationId = reservation.getId();
        this.shopId = reservation.getShop().getId();
        this.branchId = reservation.getShop().getBranch().getId();
        this.customerId = reservation.getCustomer().getId();
        this.reservationTime = TimeUtils.localDataTimeParseString(reservation.getReservationDate());
        this.people = reservation.getPeople();
        this.status = reservation.getStatus();
        this.customerName = reservation.getCustomer().getName();
        this.customerPhone = reservation.getCustomer().getPhoneNumber();
        this.branchName = reservation.getShop().getBranch().getName();
        this.shopName = reservation.getShop().getName();
    }
}
