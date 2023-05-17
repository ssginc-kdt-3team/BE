package ssginc_kdt_team3.BE.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerLoginDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.Token;
import ssginc_kdt_team3.BE.jwt.JwtAuthenticationFilter;
import ssginc_kdt_team3.BE.service.user.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserLoginController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map> login2(@RequestBody CustomerLoginDTO customerLoginDTO, HttpServletResponse response)  {

        Token token = userService.userLogin(customerLoginDTO);

        if (token != null) {
            HttpHeaders httpHeaders = new HttpHeaders();

            httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + token);

            Map<String, String> map = new HashMap();
            map.put("accessToken", token.getAccessToken());
            map.put("refreshToken", token.getRefreshToken());

            return new ResponseEntity<>(map, httpHeaders, HttpStatus.OK);
        }

        return null;

    }

    @PostMapping("/test")
    public String test(){

        return "<h1>test 통과</h1>";
    }
}
