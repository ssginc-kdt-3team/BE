package ssginc_kdt_team3.BE.controller.refreshToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.jwt.JwtTokenProvider;
import ssginc_kdt_team3.BE.service.refreshToken.JwtService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RefreshTokenController {

    private final JwtService jwtService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> validateRefreshToken(HttpServletRequest request){
//        @RequestBody HashMap<String, String> bodyJson

        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        log.info("refresh controller 실행");
//        Map<String, String> map = jwtService.validateRefreshToken(bodyJson.get("refreshToken"));
        Map<String, String> map = jwtService.validateRefreshToken(refreshToken);

        if(map.get("status").equals("402")){
            log.info("RefreshController - Refresh Token이 만료.");
//            RefreshApiResponseMessage refreshApiResponseMessage = new RefreshApiResponseMessage(map);
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.UNAUTHORIZED);
        }

        log.info("RefreshController - Refresh Token이 유효.");
//        RefreshApiResponseMessage refreshApiResponseMessage = new RefreshApiResponseMessage(map);
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }
}
