package ssginc_kdt_team3.BE.repository.deposit;

import org.springframework.data.jpa.repository.JpaRepository;
import ssginc_kdt_team3.BE.domain.Deposit;

public interface CustomerDepositRepository extends JpaRepository<Deposit, Long> {
}
