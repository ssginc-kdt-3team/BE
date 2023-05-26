package ssginc_kdt_team3.BE.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
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
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "deposit_id")
    private Deposit deposit;
}
