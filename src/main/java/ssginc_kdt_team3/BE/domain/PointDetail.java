package ssginc_kdt_team3.BE.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class PointDetail {

    @Id
    @Column(name = "point_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "point_detail_status")
    private boolean status;

    @Column(name = "point_detail_value")
    private int value;

    @Column(name = "point_detail_use_id")
    private Long detailUseId;

    @Column(name = "point_detail_operate_date")
    private LocalDateTime operateDate;

    @Column(name = "point_detail_end_date")
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "point_manage_id")
    private PointManagement pointManagement;
}
