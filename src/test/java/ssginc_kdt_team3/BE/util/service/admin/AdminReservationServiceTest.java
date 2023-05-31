package ssginc_kdt_team3.BE.util.service.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ssginc_kdt_team3.BE.DTOs.reservation.AdminReservationListDTO;
import ssginc_kdt_team3.BE.service.admin.AdminReservationService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminReservationServiceTest {

    @Autowired
    AdminReservationService service;

//    @Test
//    void showBranchAll() {
//        String type = "branch";
//        String status = "ALL";
//        PageRequest pageRequest = new PageRequest(0, 10);
//        Long id = 1L;
//        List<AdminReservationListDTO> reservationListDTOS = service.showBranchReservation(type, id, status);
//        for (AdminReservationListDTO reservationListDTO : reservationListDTOS) {
//            System.out.println("reservationListDTO = " + reservationListDTO);
//        }
//    }
//
//    @Test
//    void showBranchNoshow() {
//        String type = "branch";
//        String status = "NOSHOW";
//        Long id = 1L;
//        List<AdminReservationListDTO> reservationListDTOS = service.showBranchReservation(type, id, status);
//        for (AdminReservationListDTO reservationListDTO : reservationListDTOS) {
//            System.out.println("reservationListDTO = " + reservationListDTO);
//        }
//    }
//
//    @Test
//    void showBranchDONE() {
//        String type = "branch";
//        String status = "DONE";
//        Long id = 1L;
//        List<AdminReservationListDTO> reservationListDTOS = service.showBranchReservation(type, id, status);
//        for (AdminReservationListDTO reservationListDTO : reservationListDTOS) {
//            System.out.println("reservationListDTO = " + reservationListDTO);
//        }
//    }
//
//    @Test
//    void showBranchCANCEL() {
//        String type = "branch";
//        String status = "CANCEL";
//        Long id = 1L;
//        List<AdminReservationListDTO> reservationListDTOS = service.showBranchReservation(type, id, status);
//        for (AdminReservationListDTO reservationListDTO : reservationListDTOS) {
//            System.out.println("reservationListDTO = " + reservationListDTO);
//        }
//    }
//
//    @Test
//    void showBranchIMMINENT() {
//        String type = "branch";
//        String status = "IMMINENT";
//        Long id = 1L;
//        List<AdminReservationListDTO> reservationListDTOS = service.showBranchReservation(type, id, status);
//        for (AdminReservationListDTO reservationListDTO : reservationListDTOS) {
//            System.out.println("reservationListDTO = " + reservationListDTO);
//        }
//    }
//
//    @Test
//    void showShopAll() {
//        String type = "shop";
//        String status = "ALL";
//        Long id = 1L;
//        List<AdminReservationListDTO> reservationListDTOS = service.showBranchReservation(type, id, status);
//        for (AdminReservationListDTO reservationListDTO : reservationListDTOS) {
//            System.out.println("reservationListDTO = " + reservationListDTO);
//        }
//    }
}