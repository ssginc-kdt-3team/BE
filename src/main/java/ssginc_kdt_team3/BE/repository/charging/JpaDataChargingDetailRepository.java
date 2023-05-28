package ssginc_kdt_team3.BE.repository.charging;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.query.Param;
import ssginc_kdt_team3.BE.domain.ChargingDetail;

import java.util.List;
import java.util.Map;

public interface JpaDataChargingDetailRepository extends JpaRepository<ChargingDetail, Long> {

    @Query("SELECT SUM(CASE WHEN cd.status = true THEN cd.value ELSE -cd.value END) " +
            "FROM ChargingDetail cd " +
            "WHERE cd.chargingManagement.customer.id = :customerId")
    int findSumCharging(@Param("customerId") Long customerId);
}
