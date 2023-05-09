package ssginc_kdt_team3.BE.service.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.reservation.CustomerReservationAddDTO;

import java.time.LocalDateTime;

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
        customerReservationAddDTO.setReservationDate(LocalDateTime.now());

        //W
        boolean b = service.makeReservation(customerReservationAddDTO);

        //T
        Assertions.assertThat(b).isTrue();
    }


}