package ssginc_kdt_team3.BE.service.pointManagement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.DTOs.reservation.point.CustomerPointListDTO;
import ssginc_kdt_team3.BE.domain.*;
import ssginc_kdt_team3.BE.repository.customer.JpaDataCustomerRepository;
import ssginc_kdt_team3.BE.repository.point.JpaDataPointDetailRepository;
import ssginc_kdt_team3.BE.repository.point.JpaDataPointManagementRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class PointManagementService {

    @Value("${point.expirationDay}")
    private int expirationDay;


    private final JpaDataPointManagementRepository jpaDataPointManagementRepository;
    private final JpaDataPointDetailRepository jpaDataPointDetailRepository;
    private final JpaDataCustomerRepository customerRepository;

    public boolean getPointSave(boolean status, int value, String reason, Customer customer, LocalDateTime now) {

        try {
            PointManagement pointManagement = PointManagement.builder()
                    .status(status)
                    .value(value)
                    .changeReason(reason)
                    .customer(customer)
                    .changeDate(now.toLocalDate())
                    .endDate(now.toLocalDate().plusDays(1).plusDays(expirationDay)).build();

            PointManagement saveManagement = jpaDataPointManagementRepository.save(pointManagement);

            savePointGetDetail(saveManagement);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int showCustomerPointValue(Long customerId) {

        if (customerRepository.findById(customerId).isPresent()) {
            if (jpaDataPointDetailRepository.findSumPoint(customerId) != null) {
                return jpaDataPointDetailRepository.findSumPoint(customerId);
            }
            return 0;
        }
        return -99999;
    }

    //고객의 충전, 사용 목록
    public Page<CustomerPointListDTO> showCustomerPointGetAndUsingList(Long customerId, Pageable pageable, int dateType) {
        log.info("포인트 적립 사용 목록 조회");

        if (customerRepository.findById(customerId).isPresent()) {
            LocalDate dateLimit = LocalDate.now().minusMonths(dateType);
            Page<PointManagement> chargingManagements =
                    jpaDataPointManagementRepository.findAllByCustomer_IdAndChangeDateIsAfterOrderByChangeDateDesc(customerId,pageable, dateLimit);
            Page<CustomerPointListDTO> customerPointListDTOS = convertDto(chargingManagements);
            return customerPointListDTOS;
        }
        return null;
    }

    //고객의 포인트 적립 or 사용 목록
    public Page<CustomerPointListDTO> showCustomerPointList(Long customerId, Pageable pageable, boolean status, int dateType) {

        if (customerRepository.findById(customerId).isPresent()) {
            LocalDate dateLimit = LocalDate.now().minusMonths(dateType);
            Page<PointManagement> chargingManagements =
                    jpaDataPointManagementRepository.findAllByCustomer_IdAndStatusAndChangeDateIsAfterOrderByChangeDateDesc(customerId, status, pageable, dateLimit);
            Page<CustomerPointListDTO> customerPointListDTOS = convertDto(chargingManagements);
            return customerPointListDTOS;
        }

        return null;
    }

    public boolean savePointPayment(Deposit deposit) {

        PointManagement reservationPointManagement = PointManagement.builder()
                .changeDate(deposit.getReservation().getApplyTime().toLocalDate())
                .changeReason("예약금 결제")
                .status(false)
                .value(deposit.getPointDiscount())
                .customer(deposit.getReservation().getCustomer())
                .deposit(deposit).build();

        PointManagement saveManagement = jpaDataPointManagementRepository.save(reservationPointManagement);
        savePointPayDetail(saveManagement);
        return true;
    }

    public boolean saveExpirationPoints(Customer customer, Long value) {

        PointManagement reservationPointManagement = PointManagement.builder()
                .changeDate(LocalDate.now())
                .changeReason("포인트 만료")
                .status(false)
                .value(Integer.parseInt(Long.toString(value)))
                .customer(customer).build();

        PointManagement saveManagement = jpaDataPointManagementRepository.save(reservationPointManagement);
        savePointPayDetail(saveManagement);
        return true;
    }

    private void savePointGetDetail(PointManagement pointManagement) {
        PointDetail pointDetail = PointDetail.builder()
                .operateDate(pointManagement.getChangeDate())
                .status(true)
                .value(pointManagement.getValue())
                .endDate(pointManagement.getEndDate())
                .pointManagement(pointManagement).build();

        PointDetail detail = jpaDataPointDetailRepository.save(pointDetail);
        detail.saveUseId();
        jpaDataPointDetailRepository.save(detail);
    }

    private void savePointPayDetail(PointManagement pointManagement) {

        int payment = pointManagement.getValue();

        List<Object[]> details = jpaDataPointDetailRepository.balanceInquiry(pointManagement.getCustomer().getId());
        int i = 0;

        while (payment > 0) {
            Object[] objects = details.get(i);

            Long detailUseId = (Long) objects[0];
            Long balance = (Long) objects[1];

            if (payment > balance) {
                payment -= balance;

                PointDetail reservationPointDetail = PointDetail.builder()
                        .detailUseId(detailUseId)
                        .operateDate(pointManagement.getChangeDate())
                        .status(false)
                        .value(Integer.parseInt(Long.toString(balance)))
                        .pointManagement(pointManagement).build();

                jpaDataPointDetailRepository.save(reservationPointDetail);

            } else if (payment <= balance) {

                PointDetail reservationPointDetail = PointDetail.builder()
                        .detailUseId(detailUseId)
                        .operateDate(pointManagement.getChangeDate())
                        .status(false)
                        .value(Integer.parseInt(Long.toString(payment)))
                        .pointManagement(pointManagement).build();

                jpaDataPointDetailRepository.save(reservationPointDetail);

                payment = 0;
            }

            i += 1;
        }
    }

    private Page<CustomerPointListDTO> convertDto(Page<PointManagement>pointDetails) {

        //충전 후 14일 경과 시 환불 불가능
        log.info("convertDTO");
        List<CustomerPointListDTO> customerPointList = new ArrayList<>();

        for(PointManagement p : pointDetails){
            CustomerPointListDTO dto = new CustomerPointListDTO(p);
            customerPointList.add(dto);
        }
        return new PageImpl<>(customerPointList, pointDetails.getPageable(), pointDetails.getTotalElements());
    }
}
