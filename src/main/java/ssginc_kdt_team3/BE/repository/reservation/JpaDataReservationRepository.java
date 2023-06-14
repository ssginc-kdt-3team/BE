package ssginc_kdt_team3.BE.repository.reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface JpaDataReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select r from Reservation r where r.customer.id = :customerId and r.status = :status order by r.reservationDate desc")
    List<Reservation> findAllActive(@Param("customerId") Long customerId, @Param("status")ReservationStatus status);

    @Query("select r from Reservation r where r.customer.id = :customerId order by r.reservationDate desc")
    List<Reservation> findAllMy(@Param("customerId") Long customerId);

    @Query("select r from Reservation r where r.reservationDate <= :limit and r.status = :condition order by r.reservationDate desc")
    List<Reservation> findNoShow(@Param("limit") LocalDateTime limit, @Param("condition") ReservationStatus condition);

    int countByReservationDateAndShop_Id(LocalDateTime time, Long shopId);

//    Optional<Reservation> findByrReservationId(Long id);

    List<Reservation> findAllByStatusAndShop_IdAndReservationDateBetweenOrderByReservationDateDesc(ReservationStatus status, Long shopId, LocalDateTime startTime, LocalDateTime endTime);

    Page<Reservation> findAllByStatusAndShop_BranchIdOrderByReservationDateDesc(ReservationStatus status, Long branchId, Pageable pageable);

    Page<Reservation> findAllByStatusAndShop_IdOrderByReservationDateDesc(ReservationStatus status, Long shopId, Pageable pageable);

    Page<Reservation> findAllByShop_IdOrderByReservationDateDesc(Long shopId, Pageable pageable);

    List<Reservation> findAllByShop_IdAndReservationDateBetweenOrderByReservationDateDesc(Long shopId, LocalDateTime startTime, LocalDateTime endTime);

    Page<Reservation> findAllByShop_BranchIdOrderByReservationDateDesc(Long branchId, Pageable pageable);

//    @Query("SELECT r.status FROM Reservation r WHERE r.id = :id")
//    ReservationStatus reservationStatusFindByReservationId(@Param("id") Long id);
//    @Query("SELECT new ssginc_kdt_team3.BE.DTOs.reservation.CastCsvDTO(r.reservationDate, r.id, r.status, s.id, c.id, c.role, c.status, b.id) " +
//            "FROM Reservation r " +
//            "LEFT JOIN r.customer c " +
//            "LEFT JOIN r.shop s " +
//            "LEFT JOIN s.branch b")
//    List<CastCsvDTO> findAllByReservationCsvDate();

//    @Query("SELECT r FROM Reservation r")
//    List<LocalDateTime> findAllByReservationDateCsv();

    // 0531 이현: 고객 등급변동 조회 위해 추가
    List<Reservation> findAllByCustomer_IdAndStatusAndReservationDateBetween(Long userId, ReservationStatus status, LocalDateTime startDate, LocalDateTime nowDate);

//    @Query("SELECT new ssginc_kdt_team3.BE.DTOs.reservation.Alarm.ReservationAlmostAlarmDTO" +
//            "(r.id, r.status, r.reservationDate, c.id, c.name, c.phoneNumber) " +
//            "FROM Reservation r " +
//            "LEFT JOIN Customer c ON c.id = r.customer.id ")
//    List<ReservationAlmostAlarmDTO> findAlmostReservation();

    @Query("select r from Reservation r where (r.reservationDate >= :almostTime and r.reservationDate <= :almostTime2) and r.status = :condition")
    List<Reservation> findAlmostReservation(@Param("almostTime") LocalDateTime almostTime, @Param("almostTime2") LocalDateTime almostTime2, @Param("condition") ReservationStatus condition);

    @Query("select count(r) from Reservation r where (r.reservationDate BETWEEN :startDate AND :endDate) and r.shop.id = :shopId")
    int countAllReservation(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("shopId") Long shopId);

    @Query("select count(r) from Reservation r where (r.reservationDate BETWEEN :startDate AND :endDate) and r.status = :condition and r.shop.id = :shopId")
    int countReservation(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("condition") ReservationStatus condition, @Param("shopId") Long shopId);

    //최근 3개월 특정 시간별 노쇼 수
//    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.reservationDate >= :startDateTime AND r.reservationDate <= :endDateTime AND FUNCTION('HOUR', r.reservationDate) = :hour AND FUNCTION('MINUTE', r.reservationDate) = :minute AND r.status = :status")
//    int cntRecentlyStatus(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime, @Param("hour") int hour, @Param("minute") int minute, @Param("status") ReservationStatus status);

    @Query("SELECT COUNT(r) FROM Reservation r WHERE (r.reservationDate BETWEEN :startDateTime AND :endDateTime) AND FUNCTION('HOUR', r.reservationDate) = :hour AND FUNCTION('MINUTE', r.reservationDate) = :minute AND r.status = :status")
    int cntRecentlyStatus(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime, @Param("hour") int hour, @Param("minute") int minute, @Param("status") ReservationStatus status);

    //최근 3개월 특정 시간별 전체 예약 수
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.reservationDate >= :startDateTime AND r.reservationDate <= :endDateTime AND FUNCTION('HOUR', r.reservationDate) = :hour AND FUNCTION('MINUTE', r.reservationDate) = :minute")
    int cntRecentlyAll(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime, @Param("hour") int hour, @Param("minute") int minute);
}
