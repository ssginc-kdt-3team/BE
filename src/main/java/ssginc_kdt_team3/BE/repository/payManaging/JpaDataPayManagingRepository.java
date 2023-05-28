package ssginc_kdt_team3.BE.repository.payManaging;

import org.springframework.data.jpa.repository.JpaRepository;
import ssginc_kdt_team3.BE.domain.PaymentManaging;

public interface JpaDataPayManagingRepository extends JpaRepository<PaymentManaging, Long> {
}
