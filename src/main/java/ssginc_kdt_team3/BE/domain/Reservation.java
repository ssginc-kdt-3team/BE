package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import lombok.Setter;
import ssginc_kdt_team3.BE.enums.ReservationStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Reservation {

    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservation_date")
    private LocalDateTime reservationDate;

    @Column(name = "reservation_people")
    private int people;

    @Column(name = "reservation_child")
    private int child;

    @Column(name = "reservation_memo")
    private String memo;

    @Column(name = "reservation_status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(name = "reservation_cancel_reason")
    private String cancelReason;

    @Column(name = "reservation_apply_time")
    private LocalDateTime applyTime;

    @Column(name = "reservation_change_time")
    private LocalDateTime changeTime;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Customer customer;
}
