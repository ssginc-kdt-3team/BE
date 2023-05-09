package ssginc_kdt_team3.BE.service.Owner.reservation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.owner.reserve.ReserveDTO;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.owner.OwnerRepository;
import ssginc_kdt_team3.BE.service.owner.reservation.OwnerReserveService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OwnerReserveServiceTest {

  @Autowired
  OwnerReserveService ownerReserveService;

  @Autowired
  OwnerRepository ownerRepository;

  @Test
  void 모든_예약내역_조회() {
    List<Reservation> allFind = ownerReserveService.getAllReserve();
    System.out.println("allFind ==========>" +allFind);

  }
}