package ssginc_kdt_team3.BE.controller.cust;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.cust.CustJoinDTO;
import ssginc_kdt_team3.BE.DTOs.cust.CustLoginDTO;
import ssginc_kdt_team3.BE.service.cust.CustService;
import ssginc_kdt_team3.BE.service.cust.KakaoService;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cust")
public class CustController {
  private final CustService custService;
  private final KakaoService kakaoService;

  //회원가입
  @PostMapping("/join")
  public CustJoinDTO join(@RequestBody CustJoinDTO custJoinDTO){
    return custService.join(custJoinDTO);
  }

  /**
  5.4 회원가입 -> 중복이메일 체크 서비스에서 가져와 추가하기
  */

  @RequestMapping("/login")
  public boolean login(CustLoginDTO custLoginDTO) {
    return custService.login(custLoginDTO);
  }

  // 카카오톡 사용자에게 인가코드 받고, 그걸로 토큰 받아서, 사용자 정보 조회하고
  // 정보가 DB에 없으면 그 정보를 DB에 넣어
  // 정보가 DB에 있으면 로그인
  @RequestMapping("/kakao")
  public void kakao(String code) {
    System.out.println("인가코드: "+code);
    try {
      String access_token = kakaoService.getToken(code);
      System.out.println("토큰:" + access_token);
      Map<String, Object> userInfo = kakaoService.getUserInfo(access_token);
      System.out.println(userInfo);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

