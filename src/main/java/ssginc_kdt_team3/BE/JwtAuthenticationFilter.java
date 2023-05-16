package ssginc_kdt_team3.BE;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

//    private final AuthenticationManager authenticationManager;

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        if (token != null && jwtTokenProvider.validateAccessToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request,response);
    // 그냥 여기에 텍스트로 설명 써주셔도 돼요.
        /*
        * 지금 구현한 방식은 Tokenprovider를 바로 사용하는 건데
        * 다른 자료들은 provider Manager를 사용해서 구현하더라구요
        * 지금 토큰 발급은 받아지는데 Access토큰이랑 refresh 토큰 구분이 없어서 구분해서 받고싶습니다.
        *
        * 최초의 토큰을 발급 받을 때, accsess랑 refresh랑 두개를 발급받아야 할 것 같으넫용? 넵
        * 지금은 토큰 하나만 발급하고 있어용... 하나 더 발급할 수 있도록 해야해용.. 그런데 다른 자료들은 provider를 직접 안쓰고
        * provider로 잘 만드셨으니까.. 그냥 여기에 붙이시는게..?
        * 지금은 구현해본다고 admin만 사용하고 있는데 나중에 실제 프로젝트에서는 customer랑 점주용도 만들어야해서요..
        * 네, 충분히 이걸로 가능할꺼 같아요!
        * https://velog.io/@jkijki12/Jwt-Refresh-Token-%EC%A0%81%EC%9A%A9%EA%B8%B0
        * 이거 보면 생각보다 ㅅ쉽게... 적어놨어요
        *
        * 정리하자면
        * 로그인 (a token, r token)
        * 어떤 요청(header:a token, body:{data}
        *   -> a token 정상 시 : 정상 응답
        *   -> a token 만료 시 : "만료된 토큰입니다"
        *       -> front에서 요청(header:r token) -> r token 검증 후 정상이면 a token 재발급 (필요 시 r token 갱신)
        *           * 어떤 요청 다시 보낸다 (header: 새로운 a token, body:{data}
        *
        * 끝
        * 벨로그 주신거 참고해서 한번 더 해볼게요..
        * 천재태경님 할 수 있어요
        *
        * 천재면 고민 안할거같아요 ㅋㅋ.. 일단 해보시고, 막히는 부분에서 다시 만납시다! 넵 감사합니다.
        * manager로해서 참고하려고 하는데 참고가 잘 안되더라구요..
        * */
    }
}
