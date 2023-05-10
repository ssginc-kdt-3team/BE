package ssginc_kdt_team3.BE.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.deposit.AdminDepositDTO;
import ssginc_kdt_team3.BE.service.admin.AdminDepositService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/deposit")
public class AdminDepositController {

    private final AdminDepositService adminDepositService;

    @GetMapping("/branch/{id}/{page}")
    public ResponseEntity<Page<AdminDepositDTO>> showBranchDepositList(@PathVariable(name = "id") Long id,  @PathVariable(name = "page") int page) {

        Pageable pageable = PageRequest.of(page-1, 10);

        Optional<Page<AdminDepositDTO>> depositList = adminDepositService.findDepositList(pageable, "branch", id);

        if (depositList.isPresent()) {
            Page<AdminDepositDTO> adminDepositDTOPage = depositList.get();
            return ResponseEntity.ok(adminDepositDTOPage);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/shop/{id}/{page}")
    public ResponseEntity<Page<AdminDepositDTO>> showShopDepositList(@PathVariable(name = "id") Long id,  @PathVariable(name = "page") int page) {

        Pageable pageable = PageRequest.of(page-1, 10);

        Optional<Page<AdminDepositDTO>> depositList = adminDepositService.findDepositList(pageable, "shop", id);

        if (depositList.isPresent()) {
            Page<AdminDepositDTO> adminDepositDTOPage = depositList.get();
            return ResponseEntity.ok(adminDepositDTOPage);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
