package ssginc_kdt_team3.BE.repository.deposit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerListDTO;
import ssginc_kdt_team3.BE.DTOs.deposit.AdminDepositDTO;
import ssginc_kdt_team3.BE.domain.Deposit;

import java.util.List;

public interface AdminDepositRepository extends JpaRepository<Deposit, Long> {
    @Query("select d from Deposit d inner join Reservation r on d.reservation.id = r.id inner join Shop s on r.shop.id = s.id where s.id = :shopId")
    List<Deposit> findDepositList(@Param("shopId") Long shopId);

}
