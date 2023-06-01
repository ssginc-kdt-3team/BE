package ssginc_kdt_team3.BE.repository.point;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ssginc_kdt_team3.BE.domain.ChargingManagement;
import ssginc_kdt_team3.BE.domain.PointManagement;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface JpaDataPointManagementRepository extends JpaRepository<PointManagement, Long> {

    Page<PointManagement> findAllByCustomer_IdAndStatusAndChangeDateIsAfterOrderByChangeDateDesc(Long customerId, Boolean status, Pageable pageable, LocalDate changeDate);

    Page<PointManagement> findAllByCustomer_IdAndChangeDateIsAfterOrderByChangeDateDesc(Long customerId, Pageable pageable, LocalDate changeDate);
}
