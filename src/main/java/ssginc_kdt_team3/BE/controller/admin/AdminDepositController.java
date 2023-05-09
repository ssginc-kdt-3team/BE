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

    @GetMapping("/lists")
    public ResponseEntity<Page<AdminDepositDTO>> showDepositList(@RequestBody Map<String, String> param) {

        Pageable pageable = PageRequest.of(0, 10);

        String type = param.get("type");
        Long id = Long.parseLong(param.get("id"));

        Optional<Page<AdminDepositDTO>> depositList = adminDepositService.findDepositList(pageable, type, id);

        if (depositList.isPresent()) {
            Page<AdminDepositDTO> adminDepositDTOPage = depositList.get();
            return ResponseEntity.ok(adminDepositDTOPage);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
