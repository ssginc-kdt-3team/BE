package ssginc_kdt_team3.BE.service.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerChargingListDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerReviewListDTO;
import ssginc_kdt_team3.BE.domain.ChargingDetail;
import ssginc_kdt_team3.BE.domain.ChargingManagement;
import ssginc_kdt_team3.BE.domain.Review;
import ssginc_kdt_team3.BE.repository.charging.JpaDataChargingDetailRepository;
import ssginc_kdt_team3.BE.repository.charging.JpaDataChargingManagementRepository;
import ssginc_kdt_team3.BE.repository.customer.JpaCustomerRepository;
import ssginc_kdt_team3.BE.repository.customer.JpaDataCustomerRepository;

import java.util.ArrayList;
import java.util.Arrays;
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
            int sumCharging = chargingDetailRepository.findSumCharging(customerId);
            return sumCharging;
        }

        return -99999;
    }

    //고객의 충전, 사용 목록
    public Page<CustomerChargingListDTO> showCustomerChargingAndUsingList(Long customerId, Pageable pageable) {
        log.info("충전 사용 목록 조회");

        if (customerRepository.findById(customerId).isPresent()) {
            Page<ChargingManagement> chargingManagements = chargingManagementRepository.findAllByCustomer_Id(customerId,pageable);
            Page<CustomerChargingListDTO> customerChargingListDTOS = convertDto(chargingManagements);
            return customerChargingListDTOS;
        }
        return null;
    }

    //고객의 충전 or 사용 목록
    public Page<CustomerChargingListDTO> showCustomerChargingList(Long customerId, Pageable pageable, boolean status) {

        if (customerRepository.findById(customerId).isPresent()) {
            Page<CustomerChargingListDTO> customerChargingListDTOS = getCustomerChargingListFilterDTOS(customerId, pageable, status);
            return customerChargingListDTOS;
        }

        return null;
    }

    private Page<CustomerChargingListDTO> getCustomerChargingListFilterDTOS(Long customerId, Pageable pageable, boolean status) {
        Page<ChargingManagement> chargingManagements = chargingManagementRepository.findAllByCustomer_IdAndStatus(customerId, status, pageable);
        Page<CustomerChargingListDTO> customerChargingListDTOS = convertDto(chargingManagements);
        return customerChargingListDTOS;
    }

    private Page<CustomerChargingListDTO> convertDto(Page<ChargingManagement> chargingDetails) {
        log.info("convertDTO");
        List<CustomerChargingListDTO> customerChargingList = new ArrayList<>();

        for(ChargingManagement c : chargingDetails){
            CustomerChargingListDTO dto = new CustomerChargingListDTO(c);
            customerChargingList.add(dto);
        }
        return new PageImpl<>(customerChargingList, chargingDetails.getPageable(), chargingDetails.getTotalElements());
    }

}
