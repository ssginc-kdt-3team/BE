package ssginc_kdt_team3.BE.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class ChargingManagement {

    @Id
    @Column(name = "charging_manage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "charging_status")
    private boolean status;

    @Column(name = "charging_change_reason")
    private String changeReason;

    @Column(name = "charging_value")
    private int value;

    @Column(name = "charging_change_date")
    private LocalDateTime changeDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "deposit_id")
    private Deposit deposit;
}
