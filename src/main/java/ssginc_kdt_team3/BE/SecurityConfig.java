package ssginc_kdt_team3.BE;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.jwt.JwtAuthenticationFilter;
import ssginc_kdt_team3.BE.jwt.JwtTokenProvider;
import ssginc_kdt_team3.BE.repository.refreshToken.RefreshTokenRepository;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()  // csrf 크로스 사이트 위조 요청 설정, 비활성화해줘야만 restful을 사용할 수 있어
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //URL 관리
                .authorizeRequests()
                .antMatchers("/user/login", "/admin/login", "/refresh").permitAll()
                .antMatchers("/admin/test").hasRole("ADMIN")
                .antMatchers("/user/test").hasAnyRole("CUSTOMER","OWNER")
                .anyRequest().authenticated()
                .and()
                // JwtAuthenticationFilter를 먼저 적용
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, refreshTokenRepository), UsernamePasswordAuthenticationFilter.class);

        // super.configure(http); // 지워줌!
        // + 토큰에 저장된 유저정보를 활용하여야 하기 때문에 CustomUserDetailService 클래스를 생성합니다.
    }
}