package ssginc_kdt_team3.BE.service.pointManagement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.PointManagement;
import ssginc_kdt_team3.BE.repository.point.JpaDataPointDetailRepository;
import ssginc_kdt_team3.BE.repository.point.JpaDataPointManagementRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class PointManagementService {

    @Value("${point.expirationDay}")
    private int expirationDay;


    private final JpaDataPointManagementRepository jpaDataPointManagementRepository;
    private final JpaDataPointDetailRepository jpaDataPointDetailRepository;

//    public boolean getPointSave(boolean status, int value, String reason, Customer customer, LocalDateTime now) {
//
////        PointManagement pointManagement = PointManagement.builder()
////                .status(status)
////                .value(value)
////                .changeReason(reason)
////                .customer(customer)
////                .changeDate(now)
////                .endDate().build();
//
//    }
}
