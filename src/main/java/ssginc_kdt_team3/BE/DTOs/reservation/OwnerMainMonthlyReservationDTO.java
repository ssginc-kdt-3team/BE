package ssginc_kdt_team3.BE.DTOs.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerMainMonthlyReservationDTO {

    private int whole;
    private int noShowValue;
    private int noShowRate;
    private int doneValue;
    private int doneRate;
    private int cancelValue;
    private int cancelRate;
}
