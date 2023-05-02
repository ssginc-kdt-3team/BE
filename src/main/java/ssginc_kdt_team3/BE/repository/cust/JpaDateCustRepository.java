package ssginc_kdt_team3.BE.repository.cust;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ssginc_kdt_team3.BE.DTOs.cust.CustListDTO;
import ssginc_kdt_team3.BE.domain.Admin;
import ssginc_kdt_team3.BE.domain.Cust;

import java.util.Optional;

public interface JpaDateCustRepository extends JpaRepository<Cust, Long> {

    @Query("select new ssginc_kdt_team3.BE.DTOs.cust.CustListDTO(c.id, c.name, c.email, c.status, c.grade.name) from Cust c")
    Page<CustListDTO> findAllBy(Pageable pageable);

    Optional<Cust> findCustByEmail(String Email);

}
