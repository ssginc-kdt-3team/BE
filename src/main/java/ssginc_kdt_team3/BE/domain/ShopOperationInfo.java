package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
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
    private LocalDate out_date;

    @Column(name = "shop_seats")
    private int seats;
}
