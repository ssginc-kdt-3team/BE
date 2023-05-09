package ssginc_kdt_team3.BE.DTOs.reservation;

import lombok.Data;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.enums.ReservationStatus;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
* 임태경 고객 예약 시스템 개발용 DTO (0509)
*
* */
@Data
public class CustomerReservationAddDTO {

    @NotEmpty(message = "예약일은 필수값 입니다.")
    private LocalDateTime reservationDate;

    @NotEmpty(message = "인원 수는 필수값 입니다.")
    private int people;

    @NotEmpty
    private int child;

    private String memo;

    @NotEmpty
    private Long userId;

    @NotEmpty
    private Long shopId;

    public void setReservationInfo(Reservation reservation, Shop shop, Customer customer) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime reservationTime = LocalDateTime.parse(this.reservationDate.format(formatter), formatter);
        reservation.setReservationDate(reservationTime);
        reservation.setPeople(this.people);
        reservation.setChild(this.child);
        reservation.setMemo(this.memo);
        reservation.setStatus(ReservationStatus.RESERVATION);

        LocalDateTime parse = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
        reservation.setApplyTime(parse);
        reservation.setChangeTime(parse);
        reservation.setShop(shop);
        reservation.setCustomer(customer);
    }
}
