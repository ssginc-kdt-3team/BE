package ssginc_kdt_team3.BE.service.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import ssginc_kdt_team3.BE.DTOs.reservation.CustomerReservationAddDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.CustomerReservationUpdateDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.reservationPossibleDTO;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.deposit.DepositRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerReservationServiceTest {

    @Autowired
    CustomerReservationService service;

    @Test
    public void saveTest() {
        //G
        CustomerReservationAddDTO customerReservationAddDTO = new CustomerReservationAddDTO();

        customerReservationAddDTO.setUserId(2L);
        customerReservationAddDTO.setShopId(1L);
        customerReservationAddDTO.setPeople(4);
        customerReservationAddDTO.setChild(1);
        customerReservationAddDTO.setMemo("반반 무많이");
        customerReservationAddDTO.setReservationDate("2000-01-04 15:00:00");


        //W
        Long aLong = service.makeReservation(customerReservationAddDTO);
        Optional<Reservation> reservation = service.showOneReservation(aLong);
        Reservation findReservation = reservation.get();

        //T
        assertThat(findReservation.getMemo()).isEqualTo("반반 무많이");
    }

    @Test
    public void saveFailTest() {
        //G
        CustomerReservationAddDTO customerReservationAddDTO = new CustomerReservationAddDTO();

        customerReservationAddDTO.setUserId(2L);
        customerReservationAddDTO.setShopId(1L);
        customerReservationAddDTO.setPeople(4);
        customerReservationAddDTO.setChild(1);
        customerReservationAddDTO.setMemo("반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이");
        customerReservationAddDTO.setReservationDate("2000-01-04 15:00:00");

        //W
        Long aLong;
        try {
            aLong = service.makeReservation(customerReservationAddDTO);
        } catch (Exception e) {
            aLong = null;
        }

        //T
        assertThat(aLong).isEqualTo(null);
    }

    @Test
    public void updateTest() {
        //G
        CustomerReservationAddDTO customerReservationAddDTO = new CustomerReservationAddDTO();

        customerReservationAddDTO.setUserId(2L);
        customerReservationAddDTO.setShopId(1L);
        customerReservationAddDTO.setPeople(10);
        customerReservationAddDTO.setChild(4);
        customerReservationAddDTO.setMemo("반반 무많이");
        customerReservationAddDTO.setReservationDate("2000-01-04 15:00:00");

        Long aLong = service.makeReservation(customerReservationAddDTO);
        Optional<Reservation> reservation = service.showOneReservation(aLong);
        Reservation findReservation = reservation.get();

        //W
        CustomerReservationUpdateDTO reservationUpdateDTO = new CustomerReservationUpdateDTO(findReservation);
        reservationUpdateDTO.setMemo("반반 무없이");

        System.out.println(reservationUpdateDTO.toString());

        service.updateReservation(aLong, reservationUpdateDTO);
        Optional<Reservation> afterReservation = service.showOneReservation(aLong);
        Reservation afterFindReservation = afterReservation.get();

        //T
        assertThat(afterFindReservation.getMemo()).isEqualTo("반반 무없이");
    }

    @Test
    public void updateFailTest() {
        //G
        CustomerReservationAddDTO customerReservationAddDTO = new CustomerReservationAddDTO();

        customerReservationAddDTO.setUserId(2L);
        customerReservationAddDTO.setShopId(1L);
        customerReservationAddDTO.setPeople(10);
        customerReservationAddDTO.setChild(4);
        customerReservationAddDTO.setMemo("반반 무많이");
        customerReservationAddDTO.setReservationDate("2000-01-04 15:00:00");

        Long aLong = service.makeReservation(customerReservationAddDTO);
        Optional<Reservation> reservation = service.showOneReservation(aLong);
        Reservation findReservation = reservation.get();

        //W
        CustomerReservationUpdateDTO reservationUpdateDTO = new CustomerReservationUpdateDTO(findReservation);
        customerReservationAddDTO.setMemo("반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이");

        System.out.println(reservationUpdateDTO.toString());

        service.updateReservation(aLong, reservationUpdateDTO);
        Optional<Reservation> afterReservation = service.showOneReservation(aLong);
        Reservation afterFindReservation = afterReservation.get();

        //T
        assertThat(afterFindReservation.getMemo()).isEqualTo("반반 무많이");
    }

    @Test
    public void tooManyBabyFailTest() {
        //G
        CustomerReservationAddDTO customerReservationAddDTO = new CustomerReservationAddDTO();

        customerReservationAddDTO.setUserId(2L);
        customerReservationAddDTO.setShopId(1L);
        customerReservationAddDTO.setPeople(10);
        customerReservationAddDTO.setChild(4);
        customerReservationAddDTO.setMemo("반반 무많이");
        customerReservationAddDTO.setReservationDate("2000-01-04 15:00:00");

        Long aLong = service.makeReservation(customerReservationAddDTO);
        Optional<Reservation> reservation = service.showOneReservation(aLong);
        Reservation findReservation = reservation.get();

        //W
        CustomerReservationUpdateDTO reservationUpdateDTO = new CustomerReservationUpdateDTO(findReservation);
        reservationUpdateDTO.setPeople(2);
        reservationUpdateDTO.setChild(5);


        service.updateReservation(aLong, reservationUpdateDTO);
        Optional<Reservation> afterReservation = service.showOneReservation(aLong);
        Reservation afterFindReservation = afterReservation.get();

        //T
        assertThat(afterFindReservation.getPeople()).isEqualTo(10);
    }

    @Test
    public void allRefundTest() {
        //G
        CustomerReservationAddDTO customerReservationAddDTO = new CustomerReservationAddDTO();

        customerReservationAddDTO.setUserId(2L);
        customerReservationAddDTO.setShopId(1L);
        customerReservationAddDTO.setPeople(10);
        customerReservationAddDTO.setChild(4);
        customerReservationAddDTO.setMemo("반반 무많이");
        LocalDateTime time = LocalDateTime.now().plusHours(48);
        String s = timeToString(time);
        System.out.println("after ========================================================================================= " + s);
        customerReservationAddDTO.setReservationDate(s);

        Long aLong = service.makeReservation(customerReservationAddDTO);

        //W
        boolean b = service.customerCancel(aLong);

        //T
        Optional<Reservation> afterUpdateOptional = service.showOneReservation(aLong);
        Reservation afterUpdate = afterUpdateOptional.get();

        assertThat(afterUpdate.getStatus()).isEqualTo(ReservationStatus.CANCEL);
    }

    @Test
    public void halfRefundTest() {
        //G
        CustomerReservationAddDTO customerReservationAddDTO = new CustomerReservationAddDTO();

        customerReservationAddDTO.setUserId(2L);
        customerReservationAddDTO.setShopId(1L);
        customerReservationAddDTO.setPeople(10);
        customerReservationAddDTO.setChild(4);
        customerReservationAddDTO.setMemo("반반 무많이");
        LocalDateTime time = LocalDateTime.now().plusHours(12);
        customerReservationAddDTO.setReservationDate(timeToString(time).toString());

        Long aLong = service.makeReservation(customerReservationAddDTO);

        //W
        boolean b = service.customerCancel(aLong);

        //T
        Optional<Reservation> afterUpdateOptional = service.showOneReservation(aLong);
        Reservation afterUpdate = afterUpdateOptional.get();

        assertThat(afterUpdate.getStatus()).isEqualTo(ReservationStatus.IMMINENT);
    }

    @Test
    public void noRefundTest() {
        //G
        CustomerReservationAddDTO customerReservationAddDTO = new CustomerReservationAddDTO();

        customerReservationAddDTO.setUserId(2L);
        customerReservationAddDTO.setShopId(1L);
        customerReservationAddDTO.setPeople(10);
        customerReservationAddDTO.setChild(4);
        customerReservationAddDTO.setMemo("반반 무많이");
        LocalDateTime time = LocalDateTime.now().plusHours(4);
        customerReservationAddDTO.setReservationDate(timeToString(time).toString());

        Long aLong = service.makeReservation(customerReservationAddDTO);

        //W
        boolean b = service.customerCancel(aLong);

        //T
        Optional<Reservation> afterUpdateOptional = service.showOneReservation(aLong);
        Reservation afterUpdate = afterUpdateOptional.get();

        assertThat(afterUpdate.getStatus()).isEqualTo(ReservationStatus.IMMINENT);
    }

    private static String timeToString(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("======================================================================================================" + time.format(formatter));
        return time.format(formatter);
    }

    @Test
    public void canReservationTest() {
        List<reservationPossibleDTO> reservationPossibleDTOS = service.canReservation(1L, "2000-01-04");
        for (reservationPossibleDTO r :reservationPossibleDTOS) {
            System.out.println(r.toString());
        }
    }


}