package ssginc_kdt_team3.BE.repository.charging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssginc_kdt_team3.BE.domain.ChargingDetail;

import java.util.List;
import java.util.Optional;


public interface JpaDataChargingDetailRepository extends JpaRepository<ChargingDetail, Long> {

    @Query("SELECT SUM(CASE WHEN cd.status = true THEN cd.value ELSE -cd.value END) " +
            "FROM ChargingDetail cd " +
            "WHERE cd.chargingManagement.customer.id = :customerId")
    Integer findSumCharging(@Param("customerId") Long customerId);

    @Query("select cd from ChargingDetail cd where cd.detailUseId = (select d.detailUseId from ChargingDetail d join d.chargingManagement m where m.id = :chargingManagementId)")
    List<ChargingDetail> findChargingManagementUsingLog(@Param("chargingManagementId") Long chargingManagementId);

    @Query("SELECT cd.detailUseId, " +
            "    SUM(CASE WHEN cd.status = true THEN cd.value ELSE -cd.value END) AS total_sum " +
            "FROM ChargingDetail cd " +
            "JOIN ChargingManagement cm ON cd.chargingManagement.id = cm.id " +
            "WHERE cm.customer.id = :customerId " +
            "GROUP BY cd.detailUseId " +
            "HAVING SUM(CASE WHEN cd.status = true THEN cd.value ELSE -cd.value END) <> 0")
    List<Object[]> balanceInquiry(@Param("customerId") Long customerId);

}
