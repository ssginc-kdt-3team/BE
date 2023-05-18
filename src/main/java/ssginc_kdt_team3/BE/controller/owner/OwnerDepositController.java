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
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/owner")
@Slf4j
public class OwnerDepositController {
  private final OwnerDepositService depositService;
  @Value("${owner.pageSize}")
  private int pageSize;

  @PostMapping("/deposit/{id}/{page}")
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
}
