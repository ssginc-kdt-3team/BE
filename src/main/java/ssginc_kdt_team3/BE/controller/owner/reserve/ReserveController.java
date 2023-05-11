package ssginc_kdt_team3.BE.controller.owner.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.reservation.ReserveDTO;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;
import ssginc_kdt_team3.BE.service.owner.reservation.OwnerReserveService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owner/reserve")
public class ReserveController {
  private final OwnerReserveService reserveService;
  private final JpaDataReservationRepository jpaDataReservationRepository;

  // json으로 넘겨줘야 하니까 ResponseEntity 필요해 @RequestBody
  @GetMapping("/getPage") // 모든 예약내역 조회, 페이지값 어떻게 받아올지, 페이지 정보 Request, pageable, start end 도 필요한지 찾아봐
  public ResponseEntity<List<ReserveDTO>> reserveList(Pageable pageable) {
//    if (pageNo == null) {
//      pageNo = 1;
//    }
//    System.out.println("pageNo = " + pageNo);
    Page<Reservation> reservationPage = jpaDataReservationRepository.findAll(pageable);
    for (Reservation reservation : reservationPage) {
      System.out.println("reservation = " + reservation);
    }
    List<ReserveDTO> allReserve = reservationPage.map(r -> new ReserveDTO(r)).getContent();

    return ResponseEntity.ok(allReserve); //List로 변환시켜주는 애, json 형태로 뿌리려면 List형태 되야하니까
  }

  @GetMapping("/active") // 활성화된 예약 조회
  public List<ReserveDTO> activeReserveList() {
    List<ReserveDTO> activeReserve = reserveService.getActiveReserve();
    return activeReserve;
  }

  @RequestMapping("/activetime") // 예약시간별 조회
  public List<ReserveDTO> reserveTimeList() {
    List<ReserveDTO> reserveTime = reserveService.getReserveTime();
    return reserveTime;
  }





}