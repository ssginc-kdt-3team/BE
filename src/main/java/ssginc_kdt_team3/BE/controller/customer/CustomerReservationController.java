package ssginc_kdt_team3.BE.controller.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.deposit.AdminDepositDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.CustomerReservationAddDTO;
import ssginc_kdt_team3.BE.service.customer.CustomerReservationService;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/reservation")
public class CustomerReservationController {

    private final CustomerReservationService reservationService;

    @PostMapping("/add")
    public ResponseEntity showDepositList(@RequestBody CustomerReservationAddDTO dto) {

        Long userId = dto.getUserId();
        log.info("userid = {}", userId);

        boolean b = reservationService.makeReservation(dto);

        if (b) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
