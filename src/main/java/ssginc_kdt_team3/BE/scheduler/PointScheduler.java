package ssginc_kdt_team3.BE.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.PointDetail;
import ssginc_kdt_team3.BE.repository.point.JpaDataPointDetailRepository;
import ssginc_kdt_team3.BE.repository.point.JpaDataPointManagementRepository;
import ssginc_kdt_team3.BE.service.pointManagement.PointManagementService;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointScheduler {

    private final JpaDataPointDetailRepository pointDetailRepository;
    private final PointManagementService pointManagementService;

    @Scheduled(cron = "0 24 * * * *")
    public void updateExpiredPoint() {
        List<Object[]> objects = pointDetailRepository.expirationPoints();
        for (Object[] obj : objects) {
            Long detailId = (Long) obj[0];
            Long value = (Long) obj[1];

            Customer customer = pointDetailRepository.findById(detailId).get().getPointManagement().getCustomer();

            pointManagementService.saveExpirationPoints(customer, value);

        }
    }
}
