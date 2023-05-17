package ssginc_kdt_team3.BE.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import ssginc_kdt_team3.BE.repository.refreshToken.RefreshTokenRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "Refresh";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String url = ((HttpServletRequest)request).getRequestURL().toString();
        log.info("url ===================== {}", url);
        String accessToken = jwtTokenProvider.resolveAccessToken((HttpServletRequest) request);

        if (url.contains("/admin/")) {
            log.info("admin");
            if (accessToken != null && jwtTokenProvider.validateAccessToken(accessToken)) {
                log.info("ADMIN permit success");
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else if (accessToken != null && !jwtTokenProvider.validateAccessToken(accessToken)) {
                makeFailResponse((HttpServletResponse) response);
            }
        } else {
            log.info("customer or owner");
            if (accessToken != null && jwtTokenProvider.validateAccessToken(accessToken)) {
                Authentication authentication = jwtTokenProvider.getUserAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else if (accessToken != null && !jwtTokenProvider.validateAccessToken(accessToken)) {
                makeFailResponse((HttpServletResponse) response);
            }
        }



//        String RefreshToken = jwtTokenProvider.resolveAccessToken((HttpServletRequest) request);
//
//        if (AccessToken == null) {
//            return;
//        }
//

//        if (accessToken != null && jwtTokenProvider.validateAccessToken(accessToken)) {
//            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } else if (refreshToken != null) {
//            Optional<RefreshToken> byRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken);
//
//            if (byRefreshToken.isPresent()) {
//                RefreshToken findRefreshToken = byRefreshToken.get();
//
//                String recreateAccessToken = jwtTokenProvider.validateRefreshToken(findRefreshToken);
//
//                if ( recreateAccessToken != null) {
//                    Authentication authentication = jwtTokenProvider.getAuthentication(recreateAccessToken);
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//
//                    HttpServletResponse response1 = (HttpServletResponse) response;
//                    response1.setHeader(JwtAuthenticationFilter.AUTHORIZATION_HEADER,"Bearer " +  recreateAccessToken);
//                }
//            }
//        }



        chain.doFilter(request,response);
    }

    private void makeFailResponse(HttpServletResponse response) throws IOException {
        HttpServletResponse response1 = response;

        response1.setStatus(403);
        PrintWriter writer = response1.getWriter();
        writer.write("Expired token");
    }
}
