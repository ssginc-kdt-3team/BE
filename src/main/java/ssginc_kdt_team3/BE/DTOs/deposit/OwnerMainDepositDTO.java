package ssginc_kdt_team3.BE.DTOs.deposit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerMainDepositDTO {

    private int whole;
    private int penalty;
    private int refund;
    private int payment;
}
