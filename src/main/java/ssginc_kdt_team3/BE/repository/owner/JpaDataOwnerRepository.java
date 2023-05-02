package ssginc_kdt_team3.BE.repository.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import ssginc_kdt_team3.BE.domain.Cust;
import ssginc_kdt_team3.BE.domain.Owner;

import java.util.Optional;

public interface JpaDataOwnerRepository extends JpaRepository<Cust, Long> {

    Optional<Owner> saveAll(Owner owner);

}
