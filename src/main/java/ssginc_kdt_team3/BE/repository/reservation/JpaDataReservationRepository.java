package ssginc_kdt_team3.BE.repository.reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface JpaDataReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select r from Reservation r where r.customer.id = :customerId and r.status = :status")
    List<Reservation> findAllActive(@Param("customerId") Long customerId, @Param("status")ReservationStatus status);

    @Query("select r from Reservation r where r.customer.id = :customerId")
    List<Reservation> findAllMy(@Param("customerId") Long customerId);

    @Query("select r from Reservation r where r.reservationDate <= :limit and r.status = :condition")
    List<Reservation> findNoShow(@Param("limit") LocalDateTime limit, @Param("condition") ReservationStatus condition);

    int countByReservationDateAndShop_Id(LocalDateTime time, Long shopId);

    List<Reservation> findAllByStatusAndShop_IdAndReservationDateBetweenOrderByReservationDateDesc(ReservationStatus status, Long shopId, LocalDateTime startTime, LocalDateTime endTime);

    Page<Reservation> findAllByStatusAndShop_BranchId(ReservationStatus status, Long branchId, Pageable pageable);

    Page<Reservation> findAllByStatusAndShop_Id(ReservationStatus status, Long shopId, Pageable pageable);

    Page<Reservation> findAllByShop_Id(Long shopId, Pageable pageable);

    List<Reservation> findAllByShop_IdAndReservationDateBetweenOrderByReservationDateDesc(Long shopId, LocalDateTime startTime, LocalDateTime endTime);

    Page<Reservation> findAllByShop_BranchId(Long branchId, Pageable pageable);

    // 0531 이현: 고객 등급변동 조회 위해 추가
    List<Reservation> findAllByCustomer_IdAndStatusAndReservationDateBetween(Long userId, ReservationStatus status, LocalDateTime startDate, LocalDateTime beforeDate);

}
