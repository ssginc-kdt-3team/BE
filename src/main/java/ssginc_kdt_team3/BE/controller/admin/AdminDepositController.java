package ssginc_kdt_team3.BE.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${admin.pageSize}")
    private int pageSize;

    /*
    * branch Deposit List랑 shop Deposit List 하나로 합침
    * */

    @GetMapping("/{type}/{id}/{page}")
    public ResponseEntity<Page<AdminDepositDTO>> showDepositList(@PathVariable(name = "type") String type, @PathVariable(name = "id") Long id,  @PathVariable(name = "page") int page) {

        Pageable pageable = PageRequest.of(page-1, pageSize);

        if (type.equals("branch")) {
            Optional<Page<AdminDepositDTO>> depositPage = adminDepositService.findBranchDepositList(pageable, type, id);

            return getPageResponseEntity(depositPage);
        } else if (type.equals("shop") ) {

            Optional<Page<AdminDepositDTO>> depositPage = adminDepositService.findShopDepositList(pageable, type, id);

            return getPageResponseEntity(depositPage);
        }

        return ResponseEntity.badRequest().build();
    }

    private ResponseEntity<Page<AdminDepositDTO>> getPageResponseEntity(Optional<Page<AdminDepositDTO>> depositPage) {
        if (depositPage.isPresent()) {
            Page<AdminDepositDTO> adminDepositDTOPage = depositPage.get();
            return ResponseEntity.ok(adminDepositDTOPage);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
