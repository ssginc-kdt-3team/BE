package ssginc_kdt_team3.BE.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.admin.AdminLoginDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerLoginDTO;
import ssginc_kdt_team3.BE.JwtAuthenticationFilter;
import ssginc_kdt_team3.BE.service.admin.AdminService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminLoginController {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<Map> loginCheck(@RequestBody AdminLoginDTO loginDTO, HttpServletResponse response)  {

        String token = adminService.adminLogin(loginDTO);

        if (token != null) {
            HttpHeaders httpHeaders = new HttpHeaders();

            httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + token);

            Map<String, String> map = new HashMap();
            map.put("token", token);

            return new ResponseEntity<>(map, httpHeaders, HttpStatus.OK);
        }

        return null;

    }

}
