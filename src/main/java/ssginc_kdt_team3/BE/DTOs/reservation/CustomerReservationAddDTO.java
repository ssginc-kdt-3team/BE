package ssginc_kdt_team3.BE.DTOs.reservation;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.enums.ReservationStatus;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
* 임태경 고객 예약 시스템 개발용 DTO (0509)
*
* */
@Data
public class CustomerReservationAddDTO {

    @NotEmpty(message = "예약일은 필수값 입니다.")
    private String reservationDate;

    @Min(1)
    private int people;

    @Min(0)
    private int child;

    @Length(max = 100)
    private String memo;

    @Pattern(regexp = "[1-9]\\d{4,}", message = "포인트는 100단위로 사용가능합니다.")
    private String pointValue;

    private Long userId;

    private Long shopId;

    private Long couponId;
}
