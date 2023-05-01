package ssginc_kdt_team3.BE.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
public class OperationInfo {

    @Id
    @Column(name = "op_info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "open_time")
    private LocalDateTime openTime;

    @Column(name = "close_time")
    private LocalDateTime closeTime;

    @Column(name = "order_close")
    private LocalDateTime orderCloseTime;

    @Column(name = "open_date")
    private LocalDate openDate;

    @Column(name = "out_date")
    private LocalDate out_date;
}
