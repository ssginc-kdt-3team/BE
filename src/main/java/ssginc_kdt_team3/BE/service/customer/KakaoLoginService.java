package ssginc_kdt_team3.BE.service.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Grade;
import ssginc_kdt_team3.BE.enums.CustomerType;
import ssginc_kdt_team3.BE.enums.GradeType;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.customer.JpaDataCustomerRepository;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoLoginService {

  private final JpaDataCustomerRepository customerRepository;
  private final KakaoService kakaoService;

  public Long createUser(String access_token) throws IOException {

    // 여기서 조인을 호출해...

    Map<String, Object> userInfo = kakaoService.getUserInfo(access_token);
    String email1 = userInfo.get("email").toString();

    Optional<Customer> customerByEmail = customerRepository.findCustomerByEmail(email1);

    if(customerByEmail.isPresent()){
      return customerByEmail.get().getId();
    } else {

      Customer customer = new Customer();
      customer.setEmail(userInfo.get("email").toString());
      customer.setName(userInfo.get("nickname").toString());
      customer.setType(CustomerType.KAKAO);
      customer.setStatus(UserStatus.ACTIVE);
      customer.setRole(UserRole.CUSTOMER);

      // 필수값 임의로 넣어서 ... .만들자....


      customerRepository.save(customer);
      return customer.getId();
    }
  }

}
