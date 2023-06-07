package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.deposit.OwnerDepositDTO;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.service.owner.OwnerDepositService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/owner/deposit")
@Slf4j
public class OwnerDepositController {
  private final OwnerDepositService depositService;
  @Value("${owner.pageSize}")
  private int pageSize;

  @PostMapping("/{id}/{page}")
  public ResponseEntity<Page<OwnerDepositDTO>> getDepositList(@PathVariable(name = "page") int page,
                                                              @PathVariable(name = "id") Long ownerId,
                                                              @RequestBody Map<String, String> request) {
    String years = request.get("year");
    String months = request.get("month");
    String status = request.get("status");

    int year = Integer.parseInt(years);
    int month = Integer.parseInt(months);

    Pageable pageable = PageRequest.of(page-1, pageSize);
    ResponseEntity<Page<OwnerDepositDTO>> response = ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .body(depositService.getDeposit(ownerId, pageable, status, year, month));
    return response;
  }

  @PostMapping("/all/{id}")
  public ResponseEntity<Map<String, String>> getAllPenalty(@PathVariable(name = "id") Long ownerId,
                                            @RequestBody Map<String, String> request) {
    Map<String, String> response = new HashMap<>();
    Integer result = depositService.showMonthTotalPenalty(ownerId, request);

    if (result > 0) {
      response.put("result", Integer.toString(result));
      return ResponseEntity.ok().body(response);
    } else {

      if (result.equals(-9999)) {
        response.put("error", "잘못된 요청 타입입니다.");
        return ResponseEntity.badRequest().body(response);
      }

      if (result.equals(-9998)) {
        response.put("error", "존재하지 않는 매장입니다.");
        return ResponseEntity.badRequest().body(response);
      }
      return ResponseEntity.badRequest().build();
    }
  }

}
