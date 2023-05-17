package ssginc_kdt_team3.BE.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ssginc_kdt_team3.BE.DTOs.reservation.Token;
import ssginc_kdt_team3.BE.domain.RefreshToken;
import ssginc_kdt_team3.BE.enums.UserRole;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

import static ssginc_kdt_team3.BE.jwt.JwtAuthenticationFilter.AUTHORIZATION_HEADER;
import static ssginc_kdt_team3.BE.jwt.JwtAuthenticationFilter.REFRESH_TOKEN_HEADER;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    private String accessSecretKey = "temp";
    private String refreshSecretKey = "temp2";

    private long accessTokenValidTime = 30 * 60 * 1000L;
    private long refreshTokenValidTime = 14* 24 * 60 * 60 * 1000L;

    private final DetailService userDetailsService;

    @PostConstruct
    protected void init() {
        accessSecretKey = Base64.getEncoder().encodeToString(accessSecretKey.getBytes());
        refreshSecretKey = Base64.getEncoder().encodeToString(refreshSecretKey.getBytes());
    }

    public Token createToken(String userPk, UserRole roles) {  // userPK = email
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // 토큰 유효시각 설정
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)  // 암호화 알고리즘과, secret 값
                .compact();

        String refreshToken =  Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        return Token.builder().accessToken(accessToken).refreshToken(refreshToken).key(userPk).build();
    }

    public String recreationAccessToken(String userPk,  Object roles){

        log.info("recreationAccessToken");


        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();

        log.info("토큰 만들기전 = ");

        //Access Token
        String accessToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        log.info("만들어진 엑세스 토큰 = {}", accessToken);

        return accessToken;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        log.info("provide ADMIN userDetails.getAuthorities() = {}", userDetails.getAuthorities());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Authentication getUserAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadCustomerByEmail(this.getUserPk(token));
        log.info("provide USER userDetails.getAuthorities() = {}", userDetails.getAuthorities());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateAccessToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String validateRefreshToken(RefreshToken refreshTokenObj){
        
        // refresh 객체에서 refreshToken 추출
        String refreshToken = refreshTokenObj.getRefreshToken();
        
        try {
            // 검증
            Jws<Claims> claims = Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(refreshToken);

            //refresh 토큰의 만료시간이 지나지 않았을 경우, 새로운 access 토큰을 생성합니다.
            if (!claims.getBody().getExpiration().before(new Date())) {
                log.info("recreate ======================= claims.getBody().get(\"sub\").toString() === {}", claims.getBody().get("sub").toString());
                log.info("(UserRole) claims.getBody().get(\"roles\") === {}" , claims.getBody().get("roles"));

                return recreationAccessToken(claims.getBody().get("sub").toString(), claims.getBody().get("roles"));
            }
        }catch (Exception e) {
            //refresh 토큰이 만료되었을 경우, 로그인이 필요합니다.
            return null;
        }
        return null;
    }


    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader(REFRESH_TOKEN_HEADER);
        if (StringUtils.hasText(refreshToken) && refreshToken.startsWith("Refresh ")) {
            return refreshToken.substring(8);
        }
        return null;
    }
}
