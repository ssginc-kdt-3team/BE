package ssginc_kdt_team3.BE.service.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.reservation.charging.CustomerChargingListDTO;
import ssginc_kdt_team3.BE.domain.ChargingDetail;
import ssginc_kdt_team3.BE.domain.ChargingManagement;
import ssginc_kdt_team3.BE.repository.charging.JpaDataChargingDetailRepository;
import ssginc_kdt_team3.BE.repository.charging.JpaDataChargingManagementRepository;
import ssginc_kdt_team3.BE.repository.customer.JpaDataCustomerRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomerChargingService {

    private final JpaDataChargingDetailRepository chargingDetailRepository;
    private final JpaDataChargingManagementRepository chargingManagementRepository;
    private final JpaDataCustomerRepository customerRepository;

    public int showCustomerChargingValue(Long customerId) {

        if (customerRepository.findById(customerId).isPresent()) {
            if (chargingDetailRepository.findSumCharging(customerId) != null) {
                return chargingDetailRepository.findSumCharging(customerId);
            }

            return 0;
        }

        return -99999;
    }

    //고객의 충전, 사용 목록
    public Page<CustomerChargingListDTO> showCustomerChargingAndUsingList(Long customerId, Pageable pageable, int dateType) {
        log.info("충전 사용 목록 조회");

        if (customerRepository.findById(customerId).isPresent()) {
            LocalDateTime dateLimit = LocalDateTime.now().minusMonths(dateType);
            Page<ChargingManagement> chargingManagements =
                    chargingManagementRepository.findAllByCustomer_IdAndChangeDateIsAfterOrderByChangeDateDesc(customerId,pageable, dateLimit);
            Page<CustomerChargingListDTO> customerChargingListDTOS = convertDto(chargingManagements);
            return customerChargingListDTOS;
        }
        return null;
    }

    //고객의 충전 or 사용 목록
    public Page<CustomerChargingListDTO> showCustomerChargingList(Long customerId, Pageable pageable, boolean status, int dateType) {

        if (customerRepository.findById(customerId).isPresent()) {
            LocalDateTime dateLimit = LocalDateTime.now().minusMonths(dateType);
            Page<ChargingManagement> chargingManagements =
                    chargingManagementRepository.findAllByCustomer_IdAndStatusAndChangeDateIsAfterOrderByChangeDateDesc(customerId, status, pageable, dateLimit);
            Page<CustomerChargingListDTO> customerChargingListDTOS = convertDto(chargingManagements);
            return customerChargingListDTOS;
        }

        return null;
    }

    private Page<CustomerChargingListDTO> convertDto(Page<ChargingManagement> chargingDetails) {

        //충전 후 14일 경과 시 환불 불가능
        log.info("convertDTO");
        List<CustomerChargingListDTO> customerChargingList = new ArrayList<>();

        for(ChargingManagement c : chargingDetails){
            CustomerChargingListDTO dto = new CustomerChargingListDTO(c);
            if (c.isStatus() && c.getPaymentManaging()!=null && !c.getChangeDate().plusDays(14).isBefore(LocalDateTime.now())) {
                List<ChargingDetail> chargeDetails = chargingDetailRepository.findChargingManagementUsingLog(c.getId());
                if (chargeDetails.size() == 1) {
                    dto.setCanRefund(true);
                }
            }
            customerChargingList.add(dto);
        }
        return new PageImpl<>(customerChargingList, chargingDetails.getPageable(), chargingDetails.getTotalElements());
    }


}
