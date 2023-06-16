package ssginc_kdt_team3.BE.repository.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.reservation.CustomerReservationAddDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.CustomerReservationUpdateDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.reservationPossibleDTO;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.service.customer.CustomerReservationService;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class CustomerReservationServiceTest {

    @Autowired
    CustomerReservationService service;

    @Test//예약 잘되는지 테스트
    public void saveTest() throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        //G
        CustomerReservationAddDTO customerReservationAddDTO = new CustomerReservationAddDTO();

        customerReservationAddDTO.setUserId(77L);
        customerReservationAddDTO.setShopId(1L);
        customerReservationAddDTO.setPeople(4);
        customerReservationAddDTO.setChild(1);
        customerReservationAddDTO.setPointValue("0");
        customerReservationAddDTO.setCouponId(0L);
        customerReservationAddDTO.setMemo("반반 무많이");
        customerReservationAddDTO.setReservationDate("2023-06-14 15:00:00");


        //W
        Long aLong = service.makeReservation(customerReservationAddDTO);
        Optional<Reservation> reservation = service.showOneReservation(aLong);
        Reservation findReservation = reservation.get();

        //T
        assertThat(findReservation.getMemo()).isEqualTo("반반 무많이");
    }

    @Test
    public void saveFailTest() { //예약내용 업데이트 (요청사항 100자이상으로 해서 실패)
        //G
        CustomerReservationAddDTO customerReservationAddDTO = new CustomerReservationAddDTO();

        customerReservationAddDTO.setUserId(77L);
        customerReservationAddDTO.setShopId(1L);
        customerReservationAddDTO.setPeople(4);
        customerReservationAddDTO.setChild(1);
        customerReservationAddDTO.setPointValue("0");
        customerReservationAddDTO.setCouponId(0L);
        customerReservationAddDTO.setMemo("반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이");
        customerReservationAddDTO.setReservationDate("2000-01-04 15:00:00");

        int memoLength = customerReservationAddDTO.getMemo().length();
        log.info("요청 사항 길이 : {}",memoLength);


        //W
        Long aLong;
        try {
            aLong = service.makeReservation(customerReservationAddDTO);
        } catch (Exception e) {
            aLong = null;
        }

        //T
        assertThat(aLong).isEqualTo(null);
        //Data too long for column 'reservation_memo' at row 1
        // ↑ 결과창에
    }

    @Test // 예약내용 업데이트 (요청사항)
    public void updateTest() throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        //G
        CustomerReservationAddDTO customerReservationAddDTO = new CustomerReservationAddDTO();

        customerReservationAddDTO.setUserId(77L);
        customerReservationAddDTO.setShopId(5L);
        customerReservationAddDTO.setPeople(3);
        customerReservationAddDTO.setChild(1);
        customerReservationAddDTO.setPointValue("0");
        customerReservationAddDTO.setCouponId(0L);
        customerReservationAddDTO.setMemo("반반 무많이");
        customerReservationAddDTO.setReservationDate("2023-06-14 15:00:00");

        Long aLong = service.makeReservation(customerReservationAddDTO);
        log.info("ReservationId : {}",aLong);
        Optional<Reservation> reservation = service.showOneReservation(aLong);
        Reservation findReservation = reservation.get();

        //W
        CustomerReservationUpdateDTO reservationUpdateDTO = new CustomerReservationUpdateDTO(findReservation);
        reservationUpdateDTO.setMemo("반반 무없이");

        System.out.println(reservationUpdateDTO.toString());
        log.info("========================================");

        service.updateReservation(aLong, reservationUpdateDTO);

        Optional<Reservation> afterReservation = service.showOneReservation(aLong);

        Reservation afterFindReservation = afterReservation.get();

        //T
        assertThat(afterFindReservation.getMemo()).isEqualTo("반반 무없이");
    }

    @Test
    public void updateFailTest() throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        //G
        CustomerReservationAddDTO customerReservationAddDTO = new CustomerReservationAddDTO();

        customerReservationAddDTO.setUserId(77L);
        customerReservationAddDTO.setShopId(1L);
        customerReservationAddDTO.setPeople(10);
        customerReservationAddDTO.setChild(4);
        customerReservationAddDTO.setPointValue("0");
        customerReservationAddDTO.setCouponId(0L);
        customerReservationAddDTO.setMemo("반반 무많이");
        customerReservationAddDTO.setReservationDate("2000-01-04 15:00:00");

        Long aLong = service.makeReservation(customerReservationAddDTO);
        Optional<Reservation> reservation = service.showOneReservation(aLong);
        Reservation findReservation = reservation.get();

        //W
        CustomerReservationUpdateDTO reservationUpdateDTO = new CustomerReservationUpdateDTO(findReservation);
        reservationUpdateDTO.setMemo("반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이반반 무많이");
        //이거 요청 사항이 너무 길어서 DB에 안들어가는거 같아요
        //DTO에 변경된 요청 사항이 정상적으로 들어가는데도 DB에는 반영이 안돼요
        log.info("요청 사항 길이 : {}",reservationUpdateDTO.getMemo().length());
        log.info("요청 사항 내용 : {}",reservationUpdateDTO.getMemo());
        System.out.println(reservationUpdateDTO.toString());

        service.updateReservation(aLong, reservationUpdateDTO);
        Optional<Reservation> afterReservation = service.showOneReservation(aLong);
        Reservation afterFindReservation = afterReservation.get();

        //T
        assertThat(afterFindReservation.getMemo()).isEqualTo("반반 무많이");
    }

    @Test //어른수보다 아이수가 더 많은 경우
    public void tooManyBabyFailTest() throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        //G
        CustomerReservationAddDTO customerReservationAddDTO = new CustomerReservationAddDTO();

        customerReservationAddDTO.setUserId(77L);
        customerReservationAddDTO.setShopId(1L);
        customerReservationAddDTO.setPeople(10);
        customerReservationAddDTO.setChild(4);
        customerReservationAddDTO.setPointValue("0");
        customerReservationAddDTO.setCouponId(0L);
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

    @Test //예약 취소(24시간 이상 남은 상황) + 충전금 100%반환
    public void allRefundTest() throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        //G
        CustomerReservationAddDTO customerReservationAddDTO = new CustomerReservationAddDTO();

        customerReservationAddDTO.setUserId(77L);
        customerReservationAddDTO.setShopId(1L);
        customerReservationAddDTO.setPeople(10);
        customerReservationAddDTO.setChild(4);
        customerReservationAddDTO.setPointValue("0");
        customerReservationAddDTO.setCouponId(0L);
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

    @Test //예약 취소 + 충전금 50%반환
    public void halfRefundTest() throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        //G
        CustomerReservationAddDTO customerReservationAddDTO = new CustomerReservationAddDTO();

        customerReservationAddDTO.setUserId(77L);
        customerReservationAddDTO.setShopId(1L);
        customerReservationAddDTO.setPeople(10);
        customerReservationAddDTO.setChild(4);
        customerReservationAddDTO.setPointValue("0");
        customerReservationAddDTO.setCouponId(0L);
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

    @Test // 예약 취소 + 예약금 반환 x
    public void noRefundTest() throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        //G
        CustomerReservationAddDTO customerReservationAddDTO = new CustomerReservationAddDTO();

        customerReservationAddDTO.setUserId(77L);
        customerReservationAddDTO.setShopId(1L);
        customerReservationAddDTO.setPeople(10);
        customerReservationAddDTO.setChild(4);
        customerReservationAddDTO.setPointValue("0");
        customerReservationAddDTO.setCouponId(0L);
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
