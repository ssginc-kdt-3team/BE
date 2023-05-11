package ssginc_kdt_team3.BE.repository.owner.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssginc_kdt_team3.BE.domain.Reservation;
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

  // 조회
  public Optional<Reservation> findReserve(Long id){
    return Optional.ofNullable(em.find(Reservation.class, id));
  }

  // 변경
  public void updateReserve(Long id, Reservation reserve){
  }

  // 매장 모든 예약내역 조회: 지점정보랑 shop id가 있어야 되나 ?
  public List<Reservation> findAllReserve(){
    List<Reservation> resultReserve = em.createQuery("SELECT r FROM Reservation r", Reservation.class)
        .getResultList();
    return resultReserve;
  }

  // 활성화된 예약 조회
  public List<Reservation> findByStatus(ReservationStatus status){
    return em.createQuery("SELECT r FROM Reservation r WHERE r.status = :status", Reservation.class)
        .setParameter("status", ReservationStatus.RESERVATION)
        .getResultList();
  }

  // 당일예약 시간별 조회
  public List<Reservation> findDateAfter(LocalDateTime dateTime){
    return em.createQuery("SELECT r FROM Reservation r", Reservation.class)
        .getResultList();
  }

  // 당일예약 시간별 조회: 점심, 저녁시간 -> 파라미터 바인딩으로 특정시간의 모든 값을 다 가져와야 되지 않나?
  // 레포지토리를 이렇게 둘로 쪼갤필요가 없다. 왜냐면 starTime이 now, endTime이 1시간 뒤로 설정하면 되잖아.
  public List<Reservation> findDateBetween(LocalDateTime startTime, LocalDateTime endTime){
    return em.createQuery("SELECT r FROM Reservation r WHERE r.reservationDate BETWEEN :startTime AND :endTime", Reservation.class)
        .setParameter("startTime", startTime)
        .setParameter("endTime", endTime)
        .getResultList();
  }

}
