package ssginc_kdt_team3.BE.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    /*
    * RequestBody -> RequestParam
    * */

    @GetMapping("")
    public ResponseEntity<List<AdminReservationListDTO>> ownerJoin(@RequestParam Map<String, String> request) {

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

    // 상세 예약내역 조회
    @GetMapping("/detail/{id}") // 지점정보는 바디에서 찍으니까 디테일에 예약아이디로 넘어가야지
    public ResponseEntity<List<AdminReservationListDTO>> AdminDetail(@PathVariable("id") Long reserveId){

        List<AdminReservationListDTO> reservationDetail = reservationService.getReservationDetail(reserveId);

        return ResponseEntity.status(HttpStatus.OK).body(reservationDetail);
    }


}
