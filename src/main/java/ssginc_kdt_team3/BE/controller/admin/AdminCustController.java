//package ssginc_kdt_team3.BE.controller.admin;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import ssginc_kdt_team3.BE.DTOs.cust.CustDetailDTO;
//import ssginc_kdt_team3.BE.DTOs.cust.CustListDTO;
//import ssginc_kdt_team3.BE.DTOs.cust.CustUpdateDTO;
//import ssginc_kdt_team3.BE.domain.Cust;
//import ssginc_kdt_team3.BE.service.admin.AdminCustService;
//
//import java.text.SimpleDateFormat;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/admin/cust")
//public class AdminCustController {
//
//    private final AdminCustService custService;
//
//    @GetMapping("/findAll")
//    public ResponseEntity<Page<CustListDTO>> findAllCust() {
//        Pageable pageable = PageRequest.of(0, 5);
//        ResponseEntity<Page<CustListDTO>> response = ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(custService.findAllCust(pageable));
//        return response;
//    }
//
//        @GetMapping("/findById/{id}")
//    public Cust findOneCust(@PathVariable(name = "id") Long custId) throws JsonProcessingException {
//        CustDetailDTO custDetailDTO = custService.findCustById(custId);
//
//        Optional<Cust> custById = custService.temp(custId);
//
//        if (custById.isPresent()) {
//            Cust cust = custById.get();
//
//            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            objectMapper.setDateFormat(dateFormat);
//
//            String custJson = objectMapper.writeValueAsString(cust);
//
//            log.info("============================== {} ", cust.getAddress());
//            log.info("============================== {} ", cust.getName());
//            log.info("============================== {} ", cust.getGrade());
//            log.info("============================== {} ", cust.getBirthday());
//
//            return cust;
//        }
//
//        return null;
////
////
////
////        if (custDetailDTO.getId() != null ) {
////
////            return custDetailDTO;
////        } else {
////            return null;
////        }
//    }
//
//    @GetMapping("/findById2/{id}")
//    public Cust findOneCust2(@PathVariable(name = "id") Long custId) throws JsonProcessingException {
//        Optional<Cust> custById = custService.findCustById2(custId);
//
//        if (custById.isPresent()) {
//            return custById.get();
//        } else {
//            return null;
//        }
//    }
//
//    @GetMapping("/findByEmail")
//    public CustDetailDTO findOneCustByName(@RequestBody HashMap map) {
//        String email = map.get("email").toString();
//        log.info("email = {}", email);
//        CustDetailDTO custDetailDTO = custService.findCustByEmail(email);
//
//        if (custDetailDTO.getId() != null) {
//            return custDetailDTO;
//        } else {
//            return null;
//        }
//    }
//
//    @PostMapping("/update/{id}")
//    public boolean custUpdate(@PathVariable(name = "id") Long custId,
//                                    @RequestBody CustUpdateDTO updateDTO) {
//        boolean result = custService.updateCustInfo(custId, updateDTO);
//
//        return result;
//    }
//
//}
