package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.reservation.OwnerReservationDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.OwnerReservationDetailDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.OwnerReservationFilterListDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;
import ssginc_kdt_team3.BE.service.owner.OwnerReservationService;
import ssginc_kdt_team3.BE.service.owner.reservation.OwnerReserveService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/owner/reservation")
@RequiredArgsConstructor
public class OwnerReservationController {

    @Autowired
    private final OwnerReservationService ownerReservationService;
    private final OwnerReserveService reserveService;
    private final JpaDataReservationRepository reservationRepository;

    @Value("${owner.pageSize}")
    private int pageSize;


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
        Optional<OwnerReservationDetailDTO> ownerReservationDTO = ownerReservationService.showReservationDetail(reservationId);

        if (ownerReservationDTO.isPresent()) {
            return ResponseEntity.ok(ownerReservationDTO.get());
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/filter")
    public ResponseEntity checkNoShow(@RequestBody Map request) {
        Long ownerId = Long.parseLong(request.get("ownerId").toString());
        String status = request.get("status").toString();
        List<OwnerReservationFilterListDTO> ownerReservationNoShowListDTOS = ownerReservationService.showShopFilterList(ownerId, status);

        if (ownerReservationNoShowListDTOS.size() >= 1) {
            return ResponseEntity.ok(ownerReservationNoShowListDTOS);
        }

        return ResponseEntity.badRequest().build();
    }


    /*
    * 이현: OwnerReserveService, JpaDataReservationRepository 추가
    * */
    @GetMapping("/getall/{id}/{page}") // 모든 예약내역 조회
    public ResponseEntity<Page<OwnerReservationDTO>> reserveList(@PathVariable(name = "page") int page, @PathVariable(name = "id") Long ownerId) {
        Pageable pageable = PageRequest.of(page-1,pageSize);
        ResponseEntity<Page<OwnerReservationDTO>> response = ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(reserveService.getAllReserve(pageable,ownerId));
        return response;
    }

    @GetMapping("/active") // 활성화된 예약 조회
    public List<OwnerReservationDTO> activeReserveList() {
        List<OwnerReservationDTO> activeReserve = reserveService.getActiveReserve();
        return activeReserve;
    }

    @GetMapping("/activetime/{type}") // 당일 예약시간별 조회
    public List<OwnerReservationDTO> resTimeList(@PathVariable("type") String type) {
        List<OwnerReservationDTO> reserveTime = reserveService.getReserveTime(type);

        if(reserveTime == null){
            throw new IllegalStateException("다음 예약 정보가 존재하지 않습니다.");
        }
        return reserveTime;
    }


}
