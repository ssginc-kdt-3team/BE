package ssginc_kdt_team3.BE.service.customer;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.enums.CustomerType;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.customer.JpaDataCustomerRepository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoService {

  private final JpaDataCustomerRepository customerRepository;

  public String getToken(String code) throws IOException {
    // 인가코드로 토큰받기
    String host = "https://kauth.kakao.com/oauth/token";
    URL url = new URL(host);
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    String token = "";
    try {
      urlConnection.setRequestMethod("POST");
      urlConnection.setDoOutput(true); // 데이터 기록 알려주기

      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
      StringBuilder sb = new StringBuilder();
      sb.append("grant_type=authorization_code");
      sb.append("&client_id="); //client_id 입력
      sb.append("&redirect_uri="); //redirect_uri 입력
      sb.append("&code=" + code); // 인가코드 전달해서 토큰을 받아야해

      bw.write(sb.toString());
      bw.flush();

      int responseCode = urlConnection.getResponseCode();
      System.out.println("responseCode = " + responseCode);

      BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
      String line = "";
      String result = "";
      while ((line = br.readLine()) != null) {
        result += line;
      }
      System.out.println("result = " + result);

      // json parsing
      JSONParser parser = new JSONParser();
      JSONObject elem = (JSONObject) parser.parse(result);

      String access_token = elem.get("access_token").toString();
      String refresh_token = elem.get("refresh_token").toString();
      System.out.println("refresh_token = " + refresh_token);
      System.out.println("access_token = " + access_token);

      token = access_token;

      br.close();
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return token;
  }


  public Map<String, Object> getUserInfo(String access_token) throws IOException {
    String host = "https://kapi.kakao.com/v2/user/me";
    Map<String, Object> result = new HashMap<>();
    try {
      URL url = new URL(host);

      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setRequestProperty("Authorization", "Bearer " + access_token);
      urlConnection.setRequestMethod("GET");

      // 결과 200 : 성공
      int responseCode = urlConnection.getResponseCode();
      System.out.println("responseCode = " + responseCode);

      BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
      String line = "";
      String res = "";
      while((line=br.readLine())!=null)
      {
        res+=line;
      }

      System.out.println("res = " + res);

      JSONParser parser = new JSONParser();
      JSONObject obj = (JSONObject) parser.parse(res);
      JSONObject kakao_account = (JSONObject) obj.get("kakao_account");
      JSONObject profile = (JSONObject) kakao_account.get("profile");

      // age, birthday, gender 공백; 빈 문자열 반환처리
      String id = obj.get("id").toString();
      String nickname = profile.get("nickname").toString();
      String email = kakao_account.containsKey("email") ? kakao_account.get("email").toString() : "";
      String gender = kakao_account.containsKey("gender") ? kakao_account.get("gender").toString() : "";

      result.put("id", id);
      result.put("nickname", nickname);
      result.put("email", email);
      result.put("gender", gender);

      br.close();


    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }

    return result;
  }

  public String getAgreementInfo(String access_token)
  {
    String result = "";
    String host = "https://kapi.kakao.com/v2/user/scopes";
    try{
      URL url = new URL(host);
      HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
      urlConnection.setRequestMethod("GET");
      urlConnection.setRequestProperty("Authorization", "Bearer "+access_token);

      BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
      String line = "";
      while((line=br.readLine())!=null)
      {
        result+=line;
      }

      int responseCode = urlConnection.getResponseCode();
      System.out.println("responseCode = " + responseCode);

      // result is json format
      br.close();

    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (ProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

//  public Long createUser(String access_token) throws IOException {
//
//    Map<String, Object> userInfo = getUserInfo(access_token);
//    String email = userInfo.get("email").toString();
//
//    Optional<Customer> customerByEmail = customerRepository.findCustomerByEmail(email);
//
//    if(customerByEmail.isPresent()){
//      System.out.println("로그인 성공");
//      return customerByEmail.get().getId();
//    } else {
//
//      Customer customer = new Customer();
//      customer.setEmail(userInfo.get("email").toString());
//      customer.setName(userInfo.get("nickname").toString());
//      customer.setType(CustomerType.KAKAO);
//      customer.setStatus(UserStatus.ACTIVE);
//      customer.setRole(UserRole.CUSTOMER);
//
//      customerRepository.save(customer);
//      System.out.println("회원가입 성공");
//      return customer.getId();
//    }
//  }

}