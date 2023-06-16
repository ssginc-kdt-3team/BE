package ssginc_kdt_team3.BE.DTOs.deposit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerMainDepositDTO {
    private int last3MonthPenalty;
    private int last2MonthPenalty;
    private int lastMonthPenalty;
    private int thisMonthPenalty;
}
