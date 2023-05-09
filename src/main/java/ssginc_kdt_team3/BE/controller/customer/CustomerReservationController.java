package ssginc_kdt_team3.BE.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.deposit.AdminDepositDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.CustomerReservationAddDTO;
import ssginc_kdt_team3.BE.service.customer.CustomerReservationService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/reservation")
public class CustomerReservationController {

    private final CustomerReservationService reservationService;

    @GetMapping("/add")
    public ResponseEntity showDepositList(@RequestBody CustomerReservationAddDTO dto) {
        boolean b = reservationService.makeReservation(dto);

        if (b) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
