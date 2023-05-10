package ssginc_kdt_team3.BE.repository.deposit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssginc_kdt_team3.BE.domain.Deposit;

import java.util.List;

public interface OwnerDepositRepository extends JpaRepository<Deposit, Long> {

    @Query("select d from Deposit d where d.reservation.id = :reservationId")
    public Deposit findReservationDeposit(@Param("reservationId") Long reservationId);
}
