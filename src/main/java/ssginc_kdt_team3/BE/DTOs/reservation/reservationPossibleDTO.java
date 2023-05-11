package ssginc_kdt_team3.BE.DTOs.reservation;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class reservationPossibleDTO {
    public reservationPossibleDTO(Long id, LocalTime time, boolean possible) {
        this.id = id;
        this.time = time;
        this.possible = possible;
    }

    private Long id;
    private LocalTime time;
    private boolean possible;
}
