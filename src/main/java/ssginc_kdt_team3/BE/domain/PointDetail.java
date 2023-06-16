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
    private LocalDate operateDate;

    @Column(name = "point_detail_end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "point_manage_id")
    private PointManagement pointManagement;

    public void saveUseId() {
        this.detailUseId = this.id;
    }

    @Builder
    public PointDetail(boolean status, int value, Long detailUseId, LocalDate operateDate, LocalDate endDate, PointManagement pointManagement) {
        this.status = status;
        this.value = value;
        this.detailUseId = detailUseId;
        this.operateDate = operateDate;
        this.endDate = endDate;
        this.pointManagement = pointManagement;
    }
}
