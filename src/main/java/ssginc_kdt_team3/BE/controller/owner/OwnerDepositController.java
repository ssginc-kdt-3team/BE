package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.deposit.OwnerDepositDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerDepositService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owner")
public class OwnerDepositController {
  private final OwnerDepositService depositService;

  @GetMapping("/deposit")
  public ResponseEntity<List<OwnerDepositDTO>> getDepositList(Long shopId){
    List<OwnerDepositDTO> deposit = depositService.getDeposit(shopId);
    System.out.println("deposit======>" + deposit); // [] 이것만 찍힘
    return ResponseEntity.ok(deposit);
  } //shopId 있어야 하나? -> 그래야 해당 매장의 위약듬만 들고올 것 같은데,
   // 이미 점주가 로그인 한 상태에서 조회하는거라 상관없나?
}
