package ssginc_kdt_team3.BE.DTOs.customer;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.ChargingManagement;
import ssginc_kdt_team3.BE.util.TimeUtils;

@Data
@NoArgsConstructor
public class CustomerChargingListDTO {

    private Long id;
    private int price;
    private String dateTime;
    private String reason;
    private boolean type;
    private boolean canRefund;

    public CustomerChargingListDTO(ChargingManagement chargingManagement) {
        this.id = chargingManagement.getId();
        this.price = chargingManagement.getValue();
        this.dateTime = TimeUtils.localDataTimeParseString(chargingManagement.getChangeDate());
        this.reason = chargingManagement.getChangeReason();
        this.type = chargingManagement.isStatus();
    }
}
