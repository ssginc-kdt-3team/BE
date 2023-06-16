package ssginc_kdt_team3.BE.repository.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.enums.ReservationStatus;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OwnerRepository {

  private final EntityManager em;

  // 저장
  public Reservation save(Reservation reserve){
    em.persist(reserve);
    return reserve;
  }

  // 당일예약 시간별 조회
  public List<Reservation> findDateBetween(LocalDateTime startTime, LocalDateTime endTime, Long shopId){
    return em.createQuery("SELECT r FROM Reservation r WHERE r.reservationDate BETWEEN :startTime AND :endTime AND (r.status = :status1 OR r.status = :status2 OR r.status = :status3) AND r.shop.id = :shopId order by r.reservationDate", Reservation.class)
        .setParameter("startTime", startTime)
        .setParameter("endTime", endTime)
        .setParameter("status1", ReservationStatus.RESERVATION)
        .setParameter("status2", ReservationStatus.DONE)
        .setParameter("status3", ReservationStatus.NOSHOW)
        .setParameter("shopId", shopId)
        .getResultList();
  }

  public List<Reservation> findDateBetweenAll(LocalDateTime startTime, LocalDateTime endTime, Long shopId){
    return em.createQuery("SELECT r FROM Reservation r WHERE (r.reservationDate BETWEEN :startTime AND :endTime) AND r.shop.id = :shopId order by r.reservationDate", Reservation.class)
            .setParameter("startTime", startTime)
            .setParameter("endTime", endTime)
            .setParameter("shopId", shopId)
            .getResultList();
  }

}