package ssginc_kdt_team3.BE.repository.reservation;

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
    return em.createQuery("SELECT r FROM Reservation r WHERE r.reservationDate BETWEEN :startTime AND :endTime AND r.status = :status", Reservation.class)
        .setParameter("startTime", startTime)
        .setParameter("endTime", endTime)
        .setParameter("status", ReservationStatus.RESERVATION)
        .getResultList();
  } // @쿼리 쓰고 네이티브쿼리 트루? 디비에서 쿼리 작성해보고, 거기서 값을 불러오는지 확인하고 사용해야지
  // findAllByReservationDate, JPQL, JpaRepository가 제공해주는 레퍼런스 확인해보고, 여기 없는걸 쿼리문 쓰는거고
  // 쿼리문에서 이미 검증을 했는데 왜 Service에서 조건문으로 이전시간을 가져오고 있어,, ㅠㅠ

}