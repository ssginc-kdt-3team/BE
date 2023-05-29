package ssginc_kdt_team3.BE.DTOs.customer;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CustomerChargeDTO {

    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String customerId;

    @NotNull
    private String name;

    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String value;

    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String price;
}
