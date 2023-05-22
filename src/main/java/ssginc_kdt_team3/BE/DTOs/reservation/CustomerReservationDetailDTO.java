package ssginc_kdt_team3.BE.DTOs.reservation;

import lombok.Data;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.util.TimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class CustomerReservationDetailDTO {
    private Long reservationId;
    private String shopName;
    private String shopLocation;
    private String shopImgUrl;

    private String customerName;
    private String customerEmail;
    private String customerPhone;

    private String expectedTime;
    private int people;
    private int child;
    private String memo;

    private ReservationStatus reservationStatus;
    private int deposit;
    private String cancelReason;

    private boolean canReview;

    public CustomerReservationDetailDTO(Reservation reservation, Deposit deposit, boolean canReview) {
        this.reservationId = reservation.getId();
        this.shopName = reservation.getShop().getName();
        this.shopLocation = reservation.getShop().getLocation();
        this.shopImgUrl = reservation.getShop().getShopImg();
        this.customerName = reservation.getCustomer().getName();
        this.customerEmail = reservation.getCustomer().getEmail();
        this.customerPhone = reservation.getCustomer().getPhoneNumber();
        this.expectedTime = TimeUtils.localDataTimeParseString(reservation.getReservationDate());
        this.people = reservation.getPeople();
        this.child = reservation.getChild();
        this.memo = reservation.getMemo();
        this.reservationStatus = reservation.getStatus();
        this.deposit = deposit.getOrigin_value();
        this.cancelReason = reservation.getCancelReason();
        this.canReview = canReview;
    }

    public CustomerReservationDetailDTO() {
    }
}
