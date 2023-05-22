package ssginc_kdt_team3.BE.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.branch.BranchAddDTO;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerListDTO;
import ssginc_kdt_team3.BE.service.admin.AdminBranchService;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/branch")
public class AdminBranchController {

    private final AdminBranchService adminBranchService;

    @PostMapping("/add")
    public ResponseEntity createNewBranch(@Validated @RequestBody BranchAddDTO dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        Optional<Long> aLong = adminBranchService.addNewBranch(dto);

        if (aLong.isPresent()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
    }
}
