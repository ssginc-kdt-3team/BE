package ssginc_kdt_team3.BE.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.cust.CustDetailDTO;
import ssginc_kdt_team3.BE.DTOs.cust.CustListDTO;
import ssginc_kdt_team3.BE.DTOs.cust.CustUpdateDTO;
import ssginc_kdt_team3.BE.domain.Cust;
import ssginc_kdt_team3.BE.service.admin.AdminCustService;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCustController {

    private final AdminCustService custService;

    @GetMapping("/findAllCust")
    public Page<CustListDTO> findAllCust() {
        Pageable pageable = PageRequest.of(0, 5);
        return custService.findAllCust(pageable);
    }

    @GetMapping("/findCustById/{id}")
    public CustDetailDTO findOneCust(@PathVariable(name = "id") Long custId) throws JsonProcessingException {
        CustDetailDTO custDetailDTO = custService.findCustById(custId);


        if (custDetailDTO.getId() != null) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            objectMapper.setDateFormat(dateFormat);
//
//            String custJson = objectMapper.writeValueAsString(custById.get());
//            Cust cust = custById.get();
//
//            log.info("============================== {} ", cust.getAddress());
//            log.info("============================== {} ", cust.getName());
//            log.info("============================== {} ", cust.getGrade());
//            log.info("============================== {} ", cust.getBirthday());
            return custDetailDTO;
        } else {
            return null;
        }
    }

    @GetMapping("/findCustByEmail")
    public CustDetailDTO findOneCustByName(@RequestBody HashMap map) {
        String email = map.get("email").toString();
        log.info("email = {}", email);
        CustDetailDTO custDetailDTO = custService.findCustByEmail(email);

        if (custDetailDTO.getId() != null) {
            return custDetailDTO;
        } else {
            return null;
        }
    }

    @PostMapping("/custUpdate/{id}")
    public boolean custUpdate(@PathVariable(name = "id") Long custId,
                                    @RequestBody CustUpdateDTO updateDTO) {
        boolean result = custService.updateCustInfo(custId, updateDTO);

        return result;
    }

}
