package ssginc_kdt_team3.BE.util.service.reservation.Owner;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.service.owner.OwnerReservationService;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


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

    @Test
    void customerCancel() throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        boolean b = reservationService.customerCancel(11L);
    }

//    @Test
//    void filterCheckTest() {
//        System.out.println(reservationService.showShopFilterList(1L, "RESERVATION"));
//    }
}