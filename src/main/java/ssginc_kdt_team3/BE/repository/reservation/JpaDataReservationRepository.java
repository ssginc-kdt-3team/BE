package ssginc_kdt_team3.BE.repository.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Reservation;

public interface JpaDataReservationRepository extends JpaRepository<Reservation, Long> {

}
