package ssginc_kdt_team3.BE.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
public class StoreOperationInfo {

    @Id
    @Column(name = "st_op_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "st_open_time")
    private LocalDateTime openTime;

    @Column(name = "st_close_time")
    private LocalDateTime closeTime;

    @Column(name = "st_out_day")
    private LocalDate outDay;

    @Column(name = "st_open_day")
    private LocalDate openDay;

}
