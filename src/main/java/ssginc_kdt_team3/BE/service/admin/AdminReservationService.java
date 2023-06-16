package ssginc_kdt_team3.BE.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public Page<AdminReservationListDTO> showBranchReservation(String type, Long id, String status, Pageable pageable) {

        if (type.equals("branch")) {
            if (status.equals("ALL")) {

                Page<Reservation> allByShopBranchId = reservationRepository.findAllByShop_BranchIdOrderByReservationDateDesc(id, pageable);
                return convertDto(allByShopBranchId);
            }

            try {
                ReservationStatus reservationStatus = ReservationStatus.valueOf(status);

                //타입이 branch에 status에 해당하는 예약만 조회하는 경우
                Page<Reservation> allByStatusAndShopBranchId = reservationRepository.findAllByStatusAndShop_BranchIdOrderByReservationDateDesc(reservationStatus, id, pageable);
                return convertDto(allByStatusAndShopBranchId);

            } catch (IllegalArgumentException e) {
                //타입이 branch에 잘못된 status값이 전달된 경우
                return null;
            }
        } else if (type.equals("shop")) {
            //타입이 shop에 필터링 없이 모두 조회하는 경우
            if (status.equals("ALL")) {
                Page<Reservation> allByShopBranchId = reservationRepository.findAllByShop_IdOrderByReservationDateDesc(id, pageable);
                return convertDto(allByShopBranchId);
            }

            try {
                ReservationStatus reservationStatus = ReservationStatus.valueOf(status);

                //타입이 shop에 status에 해당하는 예약만 조회하는 경우
                Page<Reservation> allByStatusAndShopId = reservationRepository.findAllByStatusAndShop_IdOrderByReservationDateDesc(reservationStatus, id, pageable);
                return convertDto(allByStatusAndShopId);

            } catch (IllegalArgumentException e) {
                //타입이 shop에 잘못된 status값이 전달된 경우
                return null;
            }

        }
        //잘못된 타입값을 전달받은 경우
        return null;
    }

//    private static Page<AdminReservationListDTO> reservationListToDTOList(Page<AdminReservationListDTO> result, Page<Reservation> allByShopBranchId) {
//
//        for (Reservation r: allByShopBranchId) {
//            AdminReservationListDTO dto = new AdminReservationListDTO(r);
//            result.add(dto);
//        }
//
//        return result;
//    }

    // 관리자: 지점별, 매장별 상세 예약내역 조회
    public List<AdminReservationListDTO> getReservationDetail(Long reserveId) {

        Reservation reservation = reservationRepository.findById(reserveId).get();

        List<AdminReservationListDTO> result = new ArrayList<>();
        result.add(new AdminReservationListDTO(reservation));
        return result;
    }

    private Page<AdminReservationListDTO> convertDto(Page<Reservation> reservations) {
        List<AdminReservationListDTO> reservationListDTOS = new ArrayList<>();

        for(Reservation r : reservations){
            AdminReservationListDTO reservationListDTO = new AdminReservationListDTO(r);
            reservationListDTOS.add(reservationListDTO);
        }
        return new PageImpl<>(reservationListDTOS, reservations.getPageable(), reservations.getTotalElements());
    }
}
