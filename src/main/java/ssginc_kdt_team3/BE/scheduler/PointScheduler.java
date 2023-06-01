package ssginc_kdt_team3.BE.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ssginc_kdt_team3.BE.repository.point.JpaDataPointDetailRepository;
import ssginc_kdt_team3.BE.repository.point.JpaDataPointManagementRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointScheduler {

    private final JpaDataPointDetailRepository pointDetailRepository;
    private final JpaDataPointManagementRepository pointManagementRepository;

//    @Scheduled(cron = "0 24 * * * *")
//    public void updateExpiredPoint() {
//    }
}
