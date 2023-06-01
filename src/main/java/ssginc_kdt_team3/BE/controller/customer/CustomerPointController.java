package ssginc_kdt_team3.BE.controller.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.reservation.point.CustomerPointListDTO;
import ssginc_kdt_team3.BE.service.pointManagement.PointManagementService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/point")
public class CustomerPointController {

    @Value("${admin.pageSize}")
    private int pageSize;

    private final PointManagementService pointManagementService;

    @GetMapping("/list/{id}/{type}/{date}/{page}")
    public ResponseEntity showChargeList(@PathVariable(name = "id") Long customerId, @PathVariable(name = "type") String type,
                                         @PathVariable(name = "date") int dateType, @PathVariable(name = "page") int page) {
        Map<String, String> result = new HashMap<>();

        ArrayList<String> typeList = new ArrayList<>(Arrays.asList("all", "get", "lost"));
        ArrayList<Integer> dateList = new ArrayList<>(Arrays.asList(12, 1, 3, 6));
        if (typeList.contains(type) && dateList.contains(dateType)) {
            Pageable pageable = PageRequest.of(page-1, pageSize);

            if (type.equals("get")) {
                Page<CustomerPointListDTO> customerPointListDTOS = pointManagementService.showCustomerPointList(customerId, pageable, true, dateType);
                return getResponseEntity(result, customerPointListDTOS);
            } else if (type.equals("lost")) {
                Page<CustomerPointListDTO> customerPointListDTOS = pointManagementService.showCustomerPointList(customerId, pageable, false, dateType);
                return getResponseEntity(result, customerPointListDTOS);
            } else {
                Page<CustomerPointListDTO> customerPointListDTOS = pointManagementService.showCustomerPointGetAndUsingList(customerId, pageable, dateType);
                return getResponseEntity(result, customerPointListDTOS);
            }
        }

        result.put("error", "존재하지 않는 조회 타입입니다.");
        return ResponseEntity.badRequest().body(result);
    }

    @ResponseBody
    @GetMapping("/check/{id}")
    public ResponseEntity<Map<String, String>> showHoldingAmount(@PathVariable(name = "id") Long customerId) {
        int sum = pointManagementService.showCustomerPointValue(customerId);
        Map<String, String> result = new HashMap<>();

        if (sum < 0) {
            result.put("error", "존재하지 않는 사용자입니다.");
            return ResponseEntity.badRequest().body(result);
        }

        result.put("value", Integer.toString(sum));

        return ResponseEntity.ok().body(result);
    }

    private ResponseEntity<?> getResponseEntity(Map<String, String> result, Page<CustomerPointListDTO> customerChargingListDTOS) {
        if (customerChargingListDTOS != null) {
            return ResponseEntity.ok(customerChargingListDTOS);
        } else {
            result.put("error", "존재하지 않는 사용자입니다.");
            return ResponseEntity.badRequest().body(result);
        }
    }
}