package ssginc_kdt_team3.BE.DTOs.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerMainDailyReservationDTO {

    private String time;
    private int num;
    private int noShowRate;
    private int expectationNoShowNum;
}
