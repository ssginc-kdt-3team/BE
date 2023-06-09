package ssginc_kdt_team3.BE.repository.owner.reservation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.repository.reservation.OwnerRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@SpringBootTest
class OwnerRepositoryTest {

    @Autowired
    OwnerRepository ownerRepository;

    @Test
    void findDateBetween() {
        List<Reservation> reservationList = ownerRepository.findDateBetween(LocalDateTime.now(), LocalDateTime.now().plusHours(1),1L); //이건 1시간 후고, 난 1시간 이내 값을 가져와야 돼

        for (Reservation reservation : reservationList) {
            log.info("id = {}",reservation.getId());
            log.info("status = {}",reservation.getStatus());
            log.info("reservationDate = {}",reservation.getReservationDate());
            log.info("applyTime = {}",reservation.getApplyTime());
        }
    }
}