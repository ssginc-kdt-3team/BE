package ssginc_kdt_team3.BE.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerDetailDTO;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerListDTO;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerUpdateDTO;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.service.admin.AdminCustomerService;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/customer")
public class AdminCustomerController {

    private final AdminCustomerService customerService;

    @Value("${admin.pageSize}")
    private int pageSize;

    @GetMapping("/findAll/{page}")
    public ResponseEntity<Page<CustomerListDTO>> findAllCustomer( @PathVariable(name = "page") int page) {
        log.info("{}",pageSize);
        Pageable pageable = PageRequest.of(page-1,pageSize);
        ResponseEntity<Page<CustomerListDTO>> response = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(customerService.findAllCustomer(pageable));
        return response;
    }

    @GetMapping("/findById/{id}")
    public Customer findOneCustomer(@PathVariable(name = "id") Long CustomerId) throws JsonProcessingException {

        Optional<Customer> customerById = customerService.findRawCustomerById(CustomerId);

        if (customerById.isPresent()) {
            Customer Customer = customerById.get();


            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            objectMapper.setDateFormat(dateFormat);

            String CustomerJson = objectMapper.writeValueAsString(Customer);

            log.info("============================== {} ", Customer.getAddress());
            log.info("============================== {} ", Customer.getName());
            log.info("============================== {} ", Customer.getGrade());
            log.info("============================== {} ", Customer.getBirthday());

            return Customer;
        }

        return null;
//
//
//
//        if (CustomerDetailDTO.getId() != null ) {
//
//            return CustomerDetailDTO;
//        } else {
//            return null;
//        }
    }

    @PostMapping("/findByEmail")
    public CustomerDetailDTO findOneCustomerByName(@RequestBody HashMap map) {
        String email = map.get("email").toString();
        log.info("email = {}", email);
        CustomerDetailDTO customerDetailDTO = customerService.findCustomerByEmail(email);

        if (customerDetailDTO.getId() != null) {
            return customerDetailDTO;
        } else {
            return null;
        }
    }

    @PostMapping("/update/{id}")
    public boolean CustomerUpdate(@PathVariable(name = "id") Long CustomerId,
                              @RequestBody CustomerUpdateDTO updateDTO) {
        boolean result = customerService.updateCustomerInfo(CustomerId, updateDTO);

        return result;
    }

}