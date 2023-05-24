package ssginc_kdt_team3.BE.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class ChargingDetail {

    @Id
    @Column(name = "charging_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "charging_detail_status")
    private boolean status;

    @Column(name = "charging_detail_value")
    private int value;

    @Column(name = "charging_detail_use_id")
    private Long detailUseId;

    @Column(name = "charging_detail_operate_date")
    private LocalDateTime operateDate;

    @ManyToOne
    @JoinColumn(name = "charging_manage_id")
    private ChargingManagement chargingManagement;
}
