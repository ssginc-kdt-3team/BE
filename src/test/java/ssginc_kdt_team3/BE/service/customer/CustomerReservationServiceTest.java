package ssginc_kdt_team3.BE.service.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import ssginc_kdt_team3.BE.DTOs.reservation.CustomerReservationAddDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.CustomerReservationUpdateDTO;
import ssginc_kdt_team3.BE.domain.Reservation;

import java.time.LocalDateTime;
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


}