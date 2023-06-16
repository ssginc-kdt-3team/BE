package ssginc_kdt_team3.BE.repository.deposit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.enums.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DepositRepository extends JpaRepository<Deposit, Long> {

    //=============================================== 가게의 예약금 목록 조회 ===============================================
    Page<Deposit> findDepositByReservation_Shop_Id(@Param("shopId") Long shopId, Pageable pageable);

    //=============================================== 지점의 예약금 목록 조회 ===============================================
    Page<Deposit> findDepositByReservation_Shop_BranchId(@Param("branchId") Long branchId, Pageable pageable);

    //=============================================== 예약Id에 해당하는 예약금 조회 ===============================================
    @Query("select d from Deposit d where d.reservation.id = :reservationId")
    public Deposit findReservationDeposit(@Param("reservationId") Long reservationId);

    // 0517 이현: 점주의 기간별 예약금 조회기능 위해 추가
    @Query("select d from Deposit d inner join Reservation r on d.reservation.id = r.id inner join Shop s on r.shop.id = s.id where s.id = :shopId AND d.reservation.reservationDate BETWEEN :startDate AND :endDate order by d.reservation.reservationDate desc")
    List<Deposit> findShopDepositListBetween(@Param("shopId") Long shopId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    @Query("select d from Deposit d inner join Reservation r on d.reservation.id = r.id inner join Shop s on r.shop.id = s.id where s.id = :shopId And (d.reservation.reservationDate BETWEEN :startDate AND :endDate) AND d.status = :status ORDER BY d.reservation.reservationDate DESC ")
    List<Deposit> findDepositByStatusBetween(@Param("shopId") Long shopId, @Param("status") DepositStatus status, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    Page<Deposit> findDepositByReservation_Shop_IdAndReservation_ReservationDateBetween(Long shopId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<Deposit> findDepositByReservation_Shop_IdAndStatusAndReservation_ReservationDateBetween(Long shopId, DepositStatus status, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    //이번 달 받은 총 예약금
    @Query("select coalesce(sum(d.origin_value), 0) from Deposit d inner join Reservation r on d.reservation.id = r.id inner join Shop s on r.shop.id = s.id where s.id = :shopId AND d.reservation.reservationDate BETWEEN :startDate AND :endDate ")
    int findMonthlyAll(@Param("shopId") Long shopId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    //이번 달 받은 총 위약금
    @Query("select coalesce(sum(d.penaltyValue), 0) from Deposit d inner join Reservation r on d.reservation.id = r.id inner join Shop s on r.shop.id = s.id where s.id = :shopId and d.reservation.reservationDate BETWEEN :startDate AND :endDate")
    int findMonthlyPenalty(@Param("shopId") Long shopId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    //이번 달 받은 총 환불
    @Query("select coalesce(sum(d.origin_value - d.penaltyValue), 0) from Deposit d inner join Reservation r on d.reservation.id = r.id inner join Shop s on r.shop.id = s.id where s.id = :shopId and (d.reservation.reservationDate BETWEEN :startDate AND :endDate) AND d.status = :status ORDER BY d.reservation.reservationDate DESC")
    int findMonthlyRefund(@Param("shopId") Long shopId, @Param("status") DepositStatus status, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    //이번 달 받은 총 완료처리 결제금액
    @Query("select coalesce(sum(d.origin_value - d.penaltyValue), 0) from Deposit d inner join Reservation r on d.reservation.id = r.id inner join Shop s on r.shop.id = s.id where s.id = :shopId And (d.reservation.reservationDate BETWEEN :startDate AND :endDate) AND d.status = :status ORDER BY d.reservation.reservationDate DESC ")
    int findMonthlyPayment(@Param("shopId") Long shopId, @Param("status") DepositStatus status , @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);



}
