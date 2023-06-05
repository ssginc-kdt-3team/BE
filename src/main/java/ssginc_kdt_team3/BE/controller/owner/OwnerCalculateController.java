package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.reservation.OwnerReservationCalculateListDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerReservationService;

import java.util.List;

@Slf4j
@RequestMapping("/owner/calculate")
@RestController
@RequiredArgsConstructor
public class OwnerCalculateController {

    private final OwnerReservationService reservationService;

    @GetMapping("/{id}")
    public ResponseEntity<List<OwnerReservationCalculateListDTO>> showReservations(@PathVariable("id") Long shopId) {
        return ResponseEntity.ok(reservationService.showCalculateLists(shopId));
    }

    @PostMapping("/{id}")
    public ResponseEntity<String>calculateReservationPayment(@PathVariable("id") Long reservationId) {

        String result = reservationService.calculatePayment(reservationId);

        if (result.equals("잘못된 요청입니다.")) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }
}
