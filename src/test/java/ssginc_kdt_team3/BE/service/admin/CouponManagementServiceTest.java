package ssginc_kdt_team3.BE.service.admin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CouponManagementServiceTest {

  @Test
  void createNumber() throws NoSuchAlgorithmException {
    SecureRandom random = new SecureRandom();
    byte[] bytes = random.generateSeed(8).clone();
    System.out.println(bytes);

    String s = UUID.randomUUID().toString().replaceAll("-", "");
    String substring = s.substring(0, 9);
    System.out.println(substring);

  }
}