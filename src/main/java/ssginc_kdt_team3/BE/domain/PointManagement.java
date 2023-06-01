package ssginc_kdt_team3.BE.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class PointManagement {

    @Id
    @Column(name = "point_manage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "point_status")
    private boolean status;

    @Column(name = "point_change_reason")
    private String changeReason;

    @Column(name = "point_value")
    private int value;

    @Column(name = "point_change_date")
    private LocalDateTime changeDate;

    @Column(name = "point_end_date")
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "deposit_id")
    private Deposit deposit;

    @Builder
    public PointManagement(boolean status, String changeReason, int value, LocalDateTime changeDate, LocalDateTime endDate, Customer customer, Deposit deposit) {
        this.status = status;
        this.changeReason = changeReason;
        this.value = value;
        this.changeDate = changeDate;
        this.endDate = endDate;
        this.customer = customer;
        this.deposit = deposit;
    }
}
