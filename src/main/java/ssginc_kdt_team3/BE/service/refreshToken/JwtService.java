package ssginc_kdt_team3.BE.service.refreshToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.DTOs.reservation.Token;
import ssginc_kdt_team3.BE.jwt.JwtTokenProvider;
import ssginc_kdt_team3.BE.domain.RefreshToken;
import ssginc_kdt_team3.BE.repository.refreshToken.RefreshTokenRepository;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void login(Token tokenDto){

        RefreshToken refreshToken = RefreshToken.builder().keyName(tokenDto.getKey()).refreshToken(tokenDto.getRefreshToken()).build();
        String loginUserKeyName = refreshToken.getKeyName();

        if(refreshTokenRepository.existsByKeyName(loginUserKeyName)){
            log.info("기존의 존재하는 refresh 토큰 삭제");
            refreshTokenRepository.deleteByKeyName(loginUserKeyName);
        }
        refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> getRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    public Map<String, String> validateRefreshToken(String refreshToken){
        RefreshToken refreshToken1 = getRefreshToken(refreshToken).get();
        log.info("Service refreshToken1.getRefreshToken() == {}", refreshToken1.getRefreshToken());

        String createdAccessToken = jwtTokenProvider.validateRefreshToken(refreshToken1);

        log.info("createdAccessToken == {}", createdAccessToken);

        return createRefreshJson(createdAccessToken);
    }

    public Map<String, String> createRefreshJson(String createdAccessToken) {

        Map<String, String> map = new HashMap<>();
        if (createdAccessToken == null) {

            map.put("errortype", "Forbidden");
            map.put("status", "402");
            map.put("message", "Refresh 토큰이 만료되었습니다. 로그인이 필요합니다.");

            return map;
        }

        map.put("status", "200");
        map.put("message", "Refresh 토큰을 통한 Access Token 생성이 완료되었습니다.");
        map.put("accessToken", createdAccessToken);
        return map;
    }

}
