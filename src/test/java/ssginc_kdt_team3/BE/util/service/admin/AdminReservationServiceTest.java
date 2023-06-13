package ssginc_kdt_team3.BE.util.service.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ssginc_kdt_team3.BE.DTOs.reservation.AdminReservationListDTO;
import ssginc_kdt_team3.BE.service.admin.AdminReservationService;


@SpringBootTest
class AdminReservationServiceTest {

    @Autowired
    AdminReservationService service;

    @Test
    void showBranchAll() {
        String type = "branch";
        String status = "ALL";
        PageRequest pageRequest = PageRequest.of(0,10);
        Long id = 1L;
        Page<AdminReservationListDTO> reservationListDTOS = service.showBranchReservation(type, id, status,pageRequest);
        for (AdminReservationListDTO reservationListDTO : reservationListDTOS) {
            System.out.println("reservationListDTO = " + reservationListDTO);
        }
    }

    @Test
    void showBranchNoshow() {
        String type = "branch";
        String status = "NOSHOW";
        PageRequest pageRequest = PageRequest.of(0,10);
        Long id = 1L;
        Page<AdminReservationListDTO> reservationListDTOS = service.showBranchReservation(type, id, status,pageRequest);
        for (AdminReservationListDTO reservationListDTO : reservationListDTOS) {
            System.out.println("reservationListDTO = " + reservationListDTO);
        }
    }

    @Test
    void showBranchDONE() {
        String type = "branch";
        String status = "DONE";
        PageRequest pageRequest = PageRequest.of(0,10);
        Long id = 1L;
        Page<AdminReservationListDTO> reservationListDTOS = service.showBranchReservation(type, id, status,pageRequest);
        for (AdminReservationListDTO reservationListDTO : reservationListDTOS) {
            System.out.println("reservationListDTO = " + reservationListDTO);
        }
    }

    @Test
    void showBranchCANCEL() {
        String type = "branch";
        String status = "CANCEL";
        PageRequest pageRequest = PageRequest.of(0,10);
        Long id = 1L;
        Page<AdminReservationListDTO> reservationListDTOS = service.showBranchReservation(type, id, status,pageRequest);
        for (AdminReservationListDTO reservationListDTO : reservationListDTOS) {
            System.out.println("reservationListDTO = " + reservationListDTO);
        }
    }

    @Test
    void showBranchIMMINENT() {
        String type = "branch";
        String status = "IMMINENT";
        PageRequest pageRequest = PageRequest.of(0,10);
        Long id = 1L;
        Page<AdminReservationListDTO> reservationListDTOS = service.showBranchReservation(type, id, status,pageRequest);
        for (AdminReservationListDTO reservationListDTO : reservationListDTOS) {
            System.out.println("reservationListDTO = " + reservationListDTO);
        }
    }

    @Test
    void showShopAll() {
        String type = "shop";
        String status = "ALL";
        PageRequest pageRequest = PageRequest.of(0,10);
        Long id = 1L;
        Page<AdminReservationListDTO> reservationListDTOS = service.showBranchReservation(type, id, status,pageRequest);
        for (AdminReservationListDTO reservationListDTO : reservationListDTOS) {
            System.out.println("reservationListDTO = " + reservationListDTO);
        }
    }
}