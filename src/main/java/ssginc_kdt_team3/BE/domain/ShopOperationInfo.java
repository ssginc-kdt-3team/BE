package ssginc_kdt_team3.BE.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ssginc_kdt_team3.BE.util.TimeUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "shop_operation_info")
public class ShopOperationInfo {

    @Id
    @Column(name = "shop_operation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //LocalDateTime -> LocalTime으로 수정 (0502 임태경)
    @Column(name = "shop_open_time")
    private LocalTime openTime;

    //LocalDateTime -> LocalTime으로 수정 (0502 임태경)
    @Column(name = "shop_close_time")
    private LocalTime closeTime;

    //LocalDateTime -> LocalTime으로 수정 (0502 임태경)
    @Column(name = "shop_order_close")
    private LocalTime orderCloseTime;

    @Column(name = "shop_open_date")
    private LocalDate openDate;

    @Column(name = "shop_out_date")
    private LocalDate outDate;

    @Column(name = "shop_seats")
    private int seats;

    @Builder
    public ShopOperationInfo(String openTime, String closeTime, String orderCloseTime, String openDate, int seats) {
        this.openTime = TimeUtils.stringParseLocalTime(openTime);
        this.closeTime = TimeUtils.stringParseLocalTime(closeTime);
        this.orderCloseTime = TimeUtils.stringParseLocalTime(orderCloseTime);
        this.openDate = TimeUtils.stringParseLocalDate(openDate);
        this.seats = seats;
    }
}
