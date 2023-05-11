package ssginc_kdt_team3.BE.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.reservation.AdminReservationListDTO;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminReservationService {

    private final JpaDataReservationRepository reservationRepository;

    public List<AdminReservationListDTO> showBranchReservation(String type, Long id, String status) {

        List<AdminReservationListDTO> result = new ArrayList<>();


        if (type.equals("branch")) {
            if (status.equals("ALL")) {
                //타입이 branch에 필터링 없이 모두 조회하는 경우
                List<Reservation> allByShopBranchId = reservationRepository.findAllByShop_BranchId(id);
                return reservationListToDTOList(result, allByShopBranchId);
            }

            try {
                ReservationStatus reservationStatus = ReservationStatus.valueOf(status);

                //타입이 branch에 status에 해당하는 예약만 조회하는 경우
                List<Reservation> allByStatusAndShopBranchId = reservationRepository.findAllByStatusAndShop_BranchId(reservationStatus, id);
                return reservationListToDTOList(result, allByStatusAndShopBranchId);

            } catch (IllegalArgumentException e) {
                //타입이 branch에 잘못된 status값이 전달된 경우
                return result;
            }
        } else if (type.equals("shop")) {
            //타입이 shop에 필터링 없이 모두 조회하는 경우
            if (status.equals("ALL")) {
                List<Reservation> allByShopBranchId = reservationRepository.findAllByShop_Id(id);
                return reservationListToDTOList(result, allByShopBranchId);
            }

            try {
                ReservationStatus reservationStatus = ReservationStatus.valueOf(status);

                //타입이 shop에 status에 해당하는 예약만 조회하는 경우
                List<Reservation> allByStatusAndShopId = reservationRepository.findAllByStatusAndShop_Id(reservationStatus, id);
                return reservationListToDTOList(result, allByStatusAndShopId);

            } catch (IllegalArgumentException e) {
                //타입이 shop에 잘못된 status값이 전달된 경우
                return result;
            }

        }
        //잘못된 타입값을 전달받은 경우
        return null;
    }

    private static List<AdminReservationListDTO> reservationListToDTOList(List<AdminReservationListDTO> result, List<Reservation> allByShopBranchId) {

        for (Reservation r: allByShopBranchId) {
            AdminReservationListDTO dto = new AdminReservationListDTO(r);
            result.add(dto);
        }

        return result;
    }
}
