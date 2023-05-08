package ssginc_kdt_team3.BE.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
public class BranchOperationInfo {

    @Id
    @Column(name = "branch_operation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "branch_open_time")
    private LocalDateTime openTime;

    @Column(name = "branch_close_time")
    private LocalDateTime closeTime;

    @Column(name = "branch_open_day")
    private LocalDate openDay;


    @Column(name = "branch_out_day")
    private LocalDate outDay;
}
