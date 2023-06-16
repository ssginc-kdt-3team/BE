package ssginc_kdt_team3.BE.util.service.reservation.Owner;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.reservation.OwnerMainDailyReservationDTO;
import ssginc_kdt_team3.BE.service.owner.OwnerReservationService;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


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


    @Test
    void 노쇼율_계산() {
        List<OwnerMainDailyReservationDTO> ownerMainDailyReservationDTOS = reservationService.showMainDailyReservationCnt(1L);
        for (OwnerMainDailyReservationDTO a: ownerMainDailyReservationDTOS) {
            System.out.println(a.getTime());
            System.out.println(a.getNum());
            System.out.println(a.getNoShowRate()+"%");
            System.out.println(a.getExpectationNoShowNum());
            System.out.println("----------------------------------------------");
        }
    }

//    @Test
//    void filterCheckTest() {
//        System.out.println(reservationService.showShopFilterList(1L, "RESERVATION"));
//    }
}