package ssginc_kdt_team3.BE.repository.charging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ssginc_kdt_team3.BE.domain.ChargingDetail;
import ssginc_kdt_team3.BE.domain.ChargingManagement;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaDataChargingManagementRepository extends JpaRepository<ChargingManagement, Long>{

    Page<ChargingManagement> findAllByCustomer_IdAndStatusAndChangeDateIsAfterOrderByChangeDateDesc(Long customerId, Boolean status, Pageable pageable, LocalDateTime changeDate);

    Page<ChargingManagement> findAllByCustomer_IdAndChangeDateIsAfterOrderByChangeDateDesc(Long customerId, Pageable pageable, LocalDateTime changeDate);

}
