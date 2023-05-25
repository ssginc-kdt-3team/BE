package ssginc_kdt_team3.BE.service.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.DTOs.reservation.ReservationAlarmDTO;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;

import javax.persistence.NoResultException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NaverAlarmService {

    private final JpaDataReservationRepository repository;

    public List<ReservationAlarmDTO> reservationAlarmDTOList() {

        try {
            List<ReservationAlarmDTO> reservationAlarmDTOS = repository.findAllReservation();
            return reservationAlarmDTOS;
        } catch (NoResultException e) {
            throw new NoResultException("예기치 못한 오류가 발생하였습니다!");
        }
    }

}
