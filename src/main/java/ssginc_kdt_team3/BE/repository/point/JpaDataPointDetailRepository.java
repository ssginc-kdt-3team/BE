package ssginc_kdt_team3.BE.repository.point;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssginc_kdt_team3.BE.domain.PointDetail;

import java.util.List;

public interface JpaDataPointDetailRepository extends JpaRepository<PointDetail, Long> {

    @Query("SELECT SUM(CASE WHEN pd.status = true THEN pd.value ELSE -pd.value END) " +
            "FROM PointDetail pd " +
            "WHERE pd.pointManagement.customer.id = :customerId")
    Integer findSumPoint(@Param("customerId") Long customerId);

    @Query("SELECT pd.detailUseId, " +
            "   SUM(CASE WHEN pd.status = true THEN pd.value ELSE -pd.value END) AS total_sum, " +
            "   (select pd2.endDate from PointDetail pd2 where pd2.id = pd.detailUseId)" +
            "FROM PointDetail pd " +
            "JOIN PointManagement pm ON pd.pointManagement.id = pm.id " +
            "WHERE pm.customer.id = :customerId " +
            "GROUP BY pd.detailUseId " +
            "HAVING SUM(CASE WHEN pd.status = true THEN pd.value ELSE -pd.value END) <> 0")
    List<Object[]> balanceInquiry(@Param("customerId") Long customerId);

    @Query("SELECT pd.detailUseId, " +
            "   SUM(CASE WHEN pd.status = true THEN pd.value ELSE -pd.value END) AS total_sum " +
            "FROM PointDetail pd " +
            "WHERE pd.detailUseId IN " +
            "   (SELECT pd3.detailUseId FROM PointDetail pd3 " +
            "   WHERE pd3.endDate = CURRENT_DATE) " +
            "GROUP BY pd.detailUseId " +
            "HAVING SUM(CASE WHEN pd.status = true THEN pd.value ELSE -pd.value END) <> 0")
    List<Object[]> expirationPoints();
}
