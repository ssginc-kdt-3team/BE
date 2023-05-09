package ssginc_kdt_team3.BE.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.admin.AdminLoginDTO;
import ssginc_kdt_team3.BE.domain.Admin;
import ssginc_kdt_team3.BE.repository.admin.JpaDateAdminRepository;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final JpaDateAdminRepository repository;

    public Long adminLogin(AdminLoginDTO loginDTO) {

        String adminEmail = loginDTO.getEmail();
        String adminPassword = loginDTO.getPassword();
        Optional<Admin> tryAdmin = repository.findByLoginId(adminEmail);

        if (tryAdmin.isPresent() && tryAdmin.get().getPassword().equals(adminPassword)) {
            log.info("success");
            return tryAdmin.get().getId();
        } else {
            log.info("fail");
            return null;
        }
    }
}
