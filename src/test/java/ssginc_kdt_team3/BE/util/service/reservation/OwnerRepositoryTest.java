//package ssginc_kdt_team3.BE.repository.owner.reservation;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import ssginc_kdt_team3.BE.repository.reservation.OwnerRepository;
//
//import java.time.LocalDateTime;
//
//@SpringBootTest
//class OwnerRepositoryTest {
//
//  @Autowired
//  OwnerRepository ownerRepository;
//
//  @Test
//  void findDateBetween() {
//    ownerRepository.findDateBetween(LocalDateTime.now(), LocalDateTime.now().plusHours(1)); //이건 1시간 후고, 난 1시간 이내 값을 가져와야 돼
//  }
//}