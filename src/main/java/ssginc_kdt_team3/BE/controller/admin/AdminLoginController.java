package ssginc_kdt_team3.BE.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.admin.AdminLoginDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerLoginDTO;
import ssginc_kdt_team3.BE.service.admin.AdminService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminLoginController {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<Long> loginCheck(@RequestBody AdminLoginDTO loginDTO) {

        Long loginAdminId = adminService.adminLogin(loginDTO);

        if (loginAdminId != null) {
            return ResponseEntity.ok(loginAdminId);
        }

        return null;

    }

}
