package ssginc_kdt_team3.BE.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import ssginc_kdt_team3.BE.enums.ReviewStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @NotNull
    @Column(name = "review_title")
    private String title;

    @NotNull
    @Column(name = "review_contents")
    private String contents;

    @NotNull
    @Column(name = "review_")
    private int point;

    @NotNull
    @Column(name = "review_status")
    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    @NotNull
    @Column(name = "review_write_time")
    private LocalDateTime time;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
}
