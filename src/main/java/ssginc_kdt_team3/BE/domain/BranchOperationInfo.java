package ssginc_kdt_team3.BE.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
public class BranchOperationInfo {

    @Id
    @Column(name = "branch_operation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "branch_open_time")
    private LocalTime openTime;

    @Column(name = "branch_close_time")
    private LocalTime closeTime;

    @Column(name = "branch_open_day")
    private LocalDate openDay;


    @Column(name = "branch_out_day")
    private LocalDate outDay;

    @Builder
    public BranchOperationInfo(LocalTime openTime, LocalTime closeTime, LocalDate openDay) {
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.openDay = openDay;
    }
}
