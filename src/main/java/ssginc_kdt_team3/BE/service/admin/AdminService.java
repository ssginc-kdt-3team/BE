package ssginc_kdt_team3.BE.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.admin.AdminLoginDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.Token;
import ssginc_kdt_team3.BE.jwt.JwtTokenProvider;
import ssginc_kdt_team3.BE.domain.Admin;
import ssginc_kdt_team3.BE.repository.admin.JpaDateAdminRepository;
import ssginc_kdt_team3.BE.service.refreshToken.JwtService;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final JpaDateAdminRepository repository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtService jwtService;

    public Token adminLogin(AdminLoginDTO loginDTO) {

        String loginId = loginDTO.getLoginId();
        String adminPassword = loginDTO.getPassword();
        Optional<Admin> tryAdmin = repository.findByLoginId(loginId);

        if (tryAdmin.isPresent() && tryAdmin.get().getPassword().equals(adminPassword)) {
            log.info("success");

            Admin admin = tryAdmin.get();
            Token tokenDto = jwtTokenProvider.createToken(admin.getLoginId(), admin.getRoles());

            jwtService.login(tokenDto);

            return tokenDto;
//            return tryAdmin.get().getId();
        } else {
            log.info("fail");
            return null;
        }
    }
}
