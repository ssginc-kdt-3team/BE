package ssginc_kdt_team3.BE.repository.reservation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JpaDataReservationRepositoryTest {

    @Autowired
    JpaDataReservationRepository reservationRepository;

    @Test
    void findAllActive() {
        List<Reservation> allMy = reservationRepository.findAllMy(1L);

        System.out.println(allMy.size());
    }

    @Test
    void findAllMy() {

        List<Reservation> myActive = reservationRepository.findAllActive(2L, ReservationStatus.RESERVATION);

        System.out.println(myActive.size());
    }

    @Test
    void countReservationByTime() {

        int i = reservationRepository.countByReservationDateAndShop_Id(LocalDateTime.of(2000,01,04,15,00,00), 1L);

        System.out.println(i);

    }
}