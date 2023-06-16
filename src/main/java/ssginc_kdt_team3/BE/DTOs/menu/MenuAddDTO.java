package ssginc_kdt_team3.BE.DTOs.menu;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class MenuAddDTO {

    @NotNull
    private Long ownerId;

    @NotNull
    @Length(min = 1, max = 20)
    private String name;

    @NotNull
    @Range(min = 0, max = 1000000)
    private int price;
}
