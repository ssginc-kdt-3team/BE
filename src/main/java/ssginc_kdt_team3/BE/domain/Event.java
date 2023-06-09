package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import lombok.Setter;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.enums.EventStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Event {

    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_status")
    @Enumerated(EnumType.STRING)
    private EventStatus status;

    @Column(name = "event_title")
    private String title;

    @Column(name = "event_banner")
    private String bannerUrl;

    @Column(name = "event_contents")
    private String contentsUrl;

    @Column(name = "event_start")
    private LocalDateTime startDate;

    @Column(name = "event_end")
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;



}
