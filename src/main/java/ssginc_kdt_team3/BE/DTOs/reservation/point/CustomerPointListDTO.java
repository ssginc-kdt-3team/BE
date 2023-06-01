package ssginc_kdt_team3.BE.DTOs.reservation.point;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.ChargingManagement;
import ssginc_kdt_team3.BE.domain.PointManagement;
import ssginc_kdt_team3.BE.util.TimeUtils;

@Data
@NoArgsConstructor
public class CustomerPointListDTO {

    private Long id;
    private int price;
    private String dateTime;
    private String reason;
    private boolean type;

    public CustomerPointListDTO(PointManagement pointManagement) {
        this.id = pointManagement.getId();
        this.price = pointManagement.getValue();
        this.dateTime = TimeUtils.localDateParseString(pointManagement.getChangeDate());
        this.reason = pointManagement.getChangeReason();
        this.type = pointManagement.isStatus();
    }
}
