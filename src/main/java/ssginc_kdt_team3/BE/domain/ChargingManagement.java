package ssginc_kdt_team3.BE.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
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
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "deposit_id")
    private Deposit deposit;

    @ManyToOne
    @JoinColumn(name = "payment_managing_id")
    private PaymentManaging paymentManaging;

    @Builder
    public ChargingManagement(Long id, boolean status, String changeReason, int value, LocalDateTime changeDate, Customer customer, Deposit deposit, PaymentManaging paymentManaging) {
        this.id = id;
        this.status = status;
        this.changeReason = changeReason;
        this.value = value;
        this.changeDate =  changeDate;
        this.customer = customer;
        this.deposit = deposit;
        this.paymentManaging = paymentManaging;
    }
}
