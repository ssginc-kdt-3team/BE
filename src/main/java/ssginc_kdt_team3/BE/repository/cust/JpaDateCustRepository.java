package ssginc_kdt_team3.BE.repository.cust;

import org.springframework.data.jpa.repository.JpaRepository;
import ssginc_kdt_team3.BE.domain.Admin;
import ssginc_kdt_team3.BE.domain.Cust;

import java.util.Optional;

public interface JpaDateCustRepository extends JpaRepository<Cust, Long> {

    Optional<Cust> findCustByEmail(String Email);

}
