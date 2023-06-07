package ssginc_kdt_team3.BE.repository.reservation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
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

//    @Test
//    void findReservationByStatusAndBranchId() {
//        List<Reservation> allByStatusAndShopBranchId = reservationRepository.findAllByStatusAndShop_BranchId(ReservationStatus.RESERVATION, 2L);
//        for (Reservation reservation : allByStatusAndShopBranchId) {
//            System.out.println(reservation.toString());
//        }
//    }

//    @Test
//    void findReservationByBranchId() {
//        List<Reservation> allByStatusAndShopBranchId = reservationRepository.findAllByShop_BranchId(2L);
//        for (Reservation reservation : allByStatusAndShopBranchId) {
//            System.out.println(reservation.toString());
//        }
//    }
//
//    @Test
//    void findReservationByShopId() {
//        List<Reservation> allByStatusAndShopBranchId = reservationRepository.findAllByShop_Id(2L);
//        for (Reservation reservation : allByStatusAndShopBranchId) {
//            System.out.println(reservation.toString());
//        }
//    }

    @Test
    void cntReservation() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth())
                .with(LocalTime.MIN);
        LocalDateTime endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth())
                .with(LocalTime.MAX);

        int i = reservationRepository.countAllReservation(startOfMonth, endOfMonth, 1L);
        int i2 = reservationRepository.countReservation(startOfMonth, endOfMonth, ReservationStatus.DONE, 1L);
        int i3 = reservationRepository.countReservation(startOfMonth, endOfMonth, ReservationStatus.NOSHOW, 1L);
        int i4 = reservationRepository.countReservation(startOfMonth, endOfMonth, ReservationStatus.IMMINENT, 1L);
        int i5 = reservationRepository.countReservation(startOfMonth, endOfMonth, ReservationStatus.CANCEL, 1L);

        System.out.println("전체 " + i);
        System.out.println("전체 방문 " + i2);
        System.out.println("전체 노쇼 " + i3);
        System.out.println("전체 취소 " + i4+i5);
    }

    @Test
    void cntRecentlyStatus() {
        LocalDateTime minusOneMonth = LocalDateTime.now().minusMonths(3);
        LocalDateTime startOfMonth = minusOneMonth.with(TemporalAdjusters.firstDayOfMonth())
                .with(LocalTime.MIN);

        LocalDateTime minusThreeMonth = LocalDateTime.now().minusMonths(1);
        LocalDateTime endOfMonth2 = minusThreeMonth.with(TemporalAdjusters.lastDayOfMonth())
                .with(LocalTime.MAX);

        LocalTime specificTime = LocalTime.of(12, 30);

        int i = reservationRepository.cntRecentlyStatus(startOfMonth, endOfMonth2, specificTime.getHour(), specificTime.getMinute(), ReservationStatus.NOSHOW);

        System.out.println(i);
    }
}