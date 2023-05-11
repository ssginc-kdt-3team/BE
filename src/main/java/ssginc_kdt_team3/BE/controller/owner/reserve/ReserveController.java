package ssginc_kdt_team3.BE.controller.owner.reserve;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.reservation.OwnerReserveDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.ReserveTimeDTO;
import ssginc_kdt_team3.BE.service.owner.reservation.OwnerReserveService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owner")
public class ReserveController {

  private final OwnerReserveService reserveService;

  @RequestMapping("/reserve")
  public List<OwnerReserveDTO> reserveList() { // Repository에서 매개변수 안 넣었으니까 여기도 없어
    List<OwnerReserveDTO> allReserve = reserveService.getAllReserve();
    return allReserve;
  }

  @RequestMapping("/active")
  public List<ReserveTimeDTO> reserveTimeList() {
    List<ReserveTimeDTO> reserveTime = reserveService.getReserveTime();
    return reserveTime;
  }






}
