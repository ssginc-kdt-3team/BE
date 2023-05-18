package ssginc_kdt_team3.BE.repository.deposit;

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
    @Query("select d from Deposit d inner join Reservation r on d.reservation.id = r.id inner join Shop s on r.shop.id = s.id where s.id = :shopId")
    List<Deposit> findShopDepositList(@Param("shopId") Long shopId);

    //=============================================== 지점의 예약금 목록 조회 ===============================================
    @Query("select d from Deposit d where d.reservation.shop.branch.id = :branchId")
    List<Deposit> findBranchDepositList(@Param("branchId") Long branchId);

    //=============================================== 예약Id에 해당하는 예약금 조회 ===============================================
    @Query("select d from Deposit d where d.reservation.id = :reservationId")
    public Deposit findReservationDeposit(@Param("reservationId") Long reservationId);

    // 0517 이현: 점주의 기간별 예약금 조회기능 위해 추가
    @Query("select d from Deposit d inner join Reservation r on d.reservation.id = r.id inner join Shop s on r.shop.id = s.id where s.id = :shopId AND d.reservation.reservationDate BETWEEN :startDate AND :endDate ")
    List<Deposit> findShopDepositListBetween(@Param("shopId") Long shopId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    @Query("select d from Deposit d inner join Reservation r on d.reservation.id = r.id inner join Shop s on r.shop.id = s.id where s.id = :shopId And (d.reservation.reservationDate BETWEEN :startDate AND :endDate) AND d.status = :status ORDER BY d.reservation.reservationDate DESC ")
    List<Deposit> findDepositByStatusBetween(@Param("shopId") Long shopId, @Param("status") DepositStatus status, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


}
