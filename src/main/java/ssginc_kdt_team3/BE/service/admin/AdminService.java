package ssginc_kdt_team3.BE.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.admin.AdminLoginDTO;
import ssginc_kdt_team3.BE.JwtTokenProvider;
import ssginc_kdt_team3.BE.domain.Admin;
import ssginc_kdt_team3.BE.repository.admin.JpaDateAdminRepository;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final JpaDateAdminRepository repository;
//    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public String adminLogin(AdminLoginDTO loginDTO) {

        String loginId = loginDTO.getLoginId();
        String adminPassword = loginDTO.getPassword();
        Optional<Admin> tryAdmin = repository.findByLoginId(loginId);

        if (tryAdmin.isPresent() && tryAdmin.get().getPassword().equals(adminPassword)) {
            log.info("success");

            Admin admin = tryAdmin.get();

            return jwtTokenProvider.createToken(admin.getLoginId(), admin.getRoles());
//            return tryAdmin.get().getId();
        } else {
            log.info("fail");
            return null;
        }
    }
}
