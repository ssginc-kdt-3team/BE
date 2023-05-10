package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.deposit.AdminDepositDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.OwnerReservationDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;
import ssginc_kdt_team3.BE.service.owner.OwnerReservationService;

import java.util.Optional;

@RestController
@RequestMapping("/owner/reservation")
@RequiredArgsConstructor
public class OwnerReservationController {

    @Autowired
    private final OwnerReservationService ownerReservationService;

    @PostMapping("noshow/{id}")
    public ResponseEntity updateNoShow(@PathVariable(name = "id") Long reservationId) {
        boolean b = ownerReservationService.customerNoShow(reservationId);

        if (b) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("enter/{id}")
    public ResponseEntity updateDone(@PathVariable(name = "id") Long reservationId) {
        boolean b = ownerReservationService.customerCome(reservationId);

        if (b) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("reject/{id}")
    public ResponseEntity updateCancel(@PathVariable(name = "id") Long reservationId) {
        boolean b = ownerReservationService.customerCancel(reservationId);

        if (b) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable(name = "id") Long reservationId) {
        Optional<OwnerReservationDTO> ownerReservationDTO = ownerReservationService.showReservationDetail(reservationId);

        if (ownerReservationDTO.isPresent()) {
            return ResponseEntity.ok(ownerReservationDTO.get());
        }

        return ResponseEntity.badRequest().build();
    }

}
