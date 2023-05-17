package ssginc_kdt_team3.BE.repository.deposit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssginc_kdt_team3.BE.domain.Deposit;

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

}
