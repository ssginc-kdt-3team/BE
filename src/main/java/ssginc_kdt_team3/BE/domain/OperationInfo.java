package ssginc_kdt_team3.BE.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
public class OperationInfo {

    @Id
    @Column(name = "op_info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //LocalDateTime -> LocalTime으로 수정 (0502 임태경)
    @Column(name = "open_time")
    private LocalTime openTime;

    //LocalDateTime -> LocalTime으로 수정 (0502 임태경)
    @Column(name = "close_time")
    private LocalTime closeTime;

    //LocalDateTime -> LocalTime으로 수정 (0502 임태경)
    @Column(name = "order_close")
    private LocalTime orderCloseTime;

    @Column(name = "open_date")
    private LocalDate openDate;

    @Column(name = "out_date")
    private LocalDate out_date;
}
