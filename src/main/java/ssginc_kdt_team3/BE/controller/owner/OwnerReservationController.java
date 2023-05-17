package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.reservation.OwnerReservationDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerReservationService;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/owner/reservation")
@RequiredArgsConstructor
public class OwnerReservationController {

    @Autowired
    private final OwnerReservationService ownerReservationService;
    private final OwnerReservationService reserveService;

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
        Optional<OwnerReservationDTO> ownerReservationDTO = ownerReservationService.showReservationDetail(reservationId);

        if (ownerReservationDTO.isPresent()) {
            return ResponseEntity.ok(ownerReservationDTO.get());
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/list/{id}/all/{page}")
    public ResponseEntity<Page<OwnerReservationDTO>> allReservationList(@PathVariable(name ="page") int page, @PathVariable(name = "id") Long ownerId, @RequestBody Map<String,String> request) {

        Pageable pageable = PageRequest.of(page-1, pageSize);

        Page<OwnerReservationDTO> result = ownerReservationService.showShopReservationAll(ownerId, pageable, request);

        if (result != null) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/list/{id}/reservation/{page}")
    public ResponseEntity<Page<OwnerReservationDTO>> reservationReservationList(@PathVariable(name ="page") int page, @PathVariable(name = "id") Long ownerId, @RequestBody Map<String,String> request) {

        Pageable pageable = PageRequest.of(page-1, pageSize);

        Page<OwnerReservationDTO> result = ownerReservationService.showShopReservationReservation(ownerId, pageable, request);

        if (result != null) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/list/{id}/done/{page}")
    public ResponseEntity<Page<OwnerReservationDTO>> doneReservationList(@PathVariable(name ="page") int page, @PathVariable(name = "id") Long ownerId, @RequestBody Map<String,String> request) {

        Pageable pageable = PageRequest.of(page-1, pageSize);

        Page<OwnerReservationDTO> result = ownerReservationService.showShopReservationDone(ownerId, pageable, request);

        if (result != null) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/list/{id}/cancel/{page}")
    public ResponseEntity<Page<OwnerReservationDTO>> cancelReservationList(@PathVariable(name ="page") int page, @PathVariable(name = "id") Long ownerId, @RequestBody Map<String,String> request) {

        Pageable pageable = PageRequest.of(page-1, pageSize);

        Page<OwnerReservationDTO> result = ownerReservationService.showShopReservationCancel(ownerId, pageable, request);

        if (result != null) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/list/{id}/noshow/{page}")
    public ResponseEntity<Page<OwnerReservationDTO>> noshowReservationList(@PathVariable(name ="page") int page, @PathVariable(name = "id") Long ownerId, @RequestBody Map<String,String> request) {

        Pageable pageable = PageRequest.of(page-1, pageSize);

        Page<OwnerReservationDTO> result = ownerReservationService.showShopReservationNoShow(ownerId, pageable, request);

        if (result != null) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/list/{id}/imminent/{page}")
    public ResponseEntity<Page<OwnerReservationDTO>> imminentReservationList(@PathVariable(name ="page") int page, @PathVariable(name = "id") Long ownerId, @RequestBody Map<String,String> request) {

        Pageable pageable = PageRequest.of(page-1, pageSize);

        Page<OwnerReservationDTO> result = ownerReservationService.showShopReservationImminent(ownerId, pageable, request);

        if (result != null) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().build();
    }

    /*
    * 이현: OwnerReserveService, JpaDataReservationRepository 추가
    * */
//    @GetMapping("/getall/{id}/{page}") // 모든 예약내역 조회
//    public ResponseEntity<Page<OwnerReservationDTO>> reserveList(@PathVariable(name = "id") Long ownerId,
//                                                                 @PathVariable(name = "page") int page) {
//
//        ResponseEntity<Page<OwnerReservationDTO>> response = ResponseEntity.ok()
//            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//            .body(reserveService.showShopReservationAll(ownerId, pageable));
//        return response;
//    }

//    @GetMapping("/active/{id}/{page}") // 활성화된 예약 조회
//    public ResponseEntity<Page<OwnerReservationDTO>> activeReserveList(@PathVariable(name = "id") Long ownerId,
//                                                                       @PathVariable(name = "page") int page) {
//        Pageable pageable = PageRequest.of(page-1, pageSize);
//        ResponseEntity<Page<OwnerReservationDTO>> response = ResponseEntity.ok()
//            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//            .body(reserveService.getActiveReserve(pageable, ownerId));
//        return response;
//    }

    // 당일 1시간 후까지 조회
    @GetMapping("/activetime/{id}/A/{page}")
    public ResponseEntity<Page<OwnerReservationDTO>> today1HourResTimeList(@PathVariable(name = "id") Long ownerId,
                                                                 @PathVariable(name = "page") int page)
                                                                  {
        Pageable pageable = PageRequest.of(page-1, pageSize);
        ResponseEntity<Page<OwnerReservationDTO>> response = ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(reserveService.getReserveTime("A", ownerId, pageable));
        return response;
    }

    // 당일 3시간 후까지 조회
    @GetMapping("/activetime/{id}/B/{page}")
    public ResponseEntity<Page<OwnerReservationDTO>> today3HourResTimeList(@PathVariable(name = "id") Long ownerId,
                                                                 @PathVariable(name = "page") int page)
    {
        Pageable pageable = PageRequest.of(page-1, pageSize);
        ResponseEntity<Page<OwnerReservationDTO>> response = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(reserveService.getReserveTime("B", ownerId, pageable));
        return response;
    }

    // 당일 점심시간 조회
    @GetMapping("/activetime/{id}/C/{page}")
    public ResponseEntity<Page<OwnerReservationDTO>> todayLunchResTimeList(@PathVariable(name = "id") Long ownerId,
                                                                 @PathVariable(name = "page") int page)
    {
        Pageable pageable = PageRequest.of(page-1, pageSize);
        ResponseEntity<Page<OwnerReservationDTO>> response = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(reserveService.getReserveTime("C", ownerId, pageable));
        return response;
    }

    // 당일 저녁시간 조회
    @GetMapping("/activetime/{id}/D/{page}")
    public ResponseEntity<Page<OwnerReservationDTO>> todayDinerResTimeList(@PathVariable(name = "id") Long ownerId,
                                                                 @PathVariable(name = "page") int page)
    {
        Pageable pageable = PageRequest.of(page-1, pageSize);
        ResponseEntity<Page<OwnerReservationDTO>> response = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(reserveService.getReserveTime("D", ownerId, pageable));
        return response;
    }

    // 당일 전체시간 조회
    @GetMapping("/activetime/{id}/E/{page}")
    public ResponseEntity<Page<OwnerReservationDTO>> todayAllResTimeList(@PathVariable(name = "id") Long ownerId,
                                                                 @PathVariable(name = "page") int page)
    {
        Pageable pageable = PageRequest.of(page-1, pageSize);
        ResponseEntity<Page<OwnerReservationDTO>> response = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(reserveService.getReserveTime("E", ownerId, pageable));
        return response;
    }


}
