package ssginc_kdt_team3.BE.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerLoginDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.Token;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.User;
import ssginc_kdt_team3.BE.jwt.JwtTokenProvider;
import ssginc_kdt_team3.BE.repository.JpaUserRepository;
import ssginc_kdt_team3.BE.service.refreshToken.JwtService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final JpaUserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtService jwtService;

    @Transactional
    public Token userLogin(CustomerLoginDTO loginDTO) {

        String email = loginDTO.getEmail();
        log.info("loginDTO.getEmail() = {}", loginDTO.getEmail());
        String password = loginDTO.getPassword();
        Optional<User> byEmail = userRepository.findByEmail(email);

        if (byEmail.isPresent() && byEmail.get().getPassword().equals(password)) {
            log.info("success");

            User user = byEmail.get();
            Token tokenDto = jwtTokenProvider.createToken(user.getEmail(), user.getRole());

            jwtService.login(tokenDto);

            return tokenDto;
//            return tryAdmin.get().getId();
        } else {
            log.info("fail");
            return null;
        }
    }
}
