package ssginc_kdt_team3.BE.service.Owner;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;
import ssginc_kdt_team3.BE.service.owner.OwnerReservationService;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OwnerReservationServiceTest {

    @Autowired
    OwnerReservationService reservationService;

    @Test
    void customerCome() {
        boolean b = reservationService.customerCome(14L);
    }

    @Test
    void customerNoShow() {
        boolean b = reservationService.customerNoShow(10L);
    }
}