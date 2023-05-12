package ssginc_kdt_team3.BE.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.AdminReservationListDTO;
import ssginc_kdt_team3.BE.service.admin.AdminReservationService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/reservation")
public class AdminReservationController {

    private final AdminReservationService reservationService;

    @GetMapping("")
    public ResponseEntity<List<AdminReservationListDTO>> ownerJoin(@RequestBody Map request ) {

        /*
        *  request
        * {
        *   type : "branch" "shop"
        *   id : 1
        *   status : ALL, RESERVATION, DONE, CANCEL, IMMINENT, NOSHOW 중 1
        * }
        * */

        String type = request.get("type").toString();
        String status = request.get("status").toString();
        long id = Long.parseLong(request.get("id").toString());

        List<AdminReservationListDTO> reservationListDTOS = reservationService.showBranchReservation(type, id, status);

        if (reservationListDTOS != null) {
            return ResponseEntity.ok(reservationListDTOS);
        }

        return ResponseEntity.badRequest().build();

    }
}
