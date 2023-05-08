package ssginc_kdt_team3.BE.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.customer.CustomeromerDetailDTO;
import ssginc_kdt_team3.BE.DTOs.customer.CustomeromerListDTO;
import ssginc_kdt_team3.BE.DTOs.customer.CustomeromerUpdateDTO;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.service.admin.AdminCustomerService;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/Customer")
public class AdminCustomerController {

    private final AdminCustomerService CustomerService;

    @GetMapping("/findAll")
    public ResponseEntity<Page<CustomeromerListDTO>> findAllCustomer() {
        Pageable pageable = PageRequest.of(0, 5);
        ResponseEntity<Page<CustomeromerListDTO>> response = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(CustomerService.findAllCustomer(pageable));
        return response;
    }

    @GetMapping("/findById/{id}")
    public Customer findOneCustomer(@PathVariable(name = "id") Long CustomerId) throws JsonProcessingException {
        CustomeromerDetailDTO CustomeromerDetailDTO = CustomerService.findCustomerById(CustomerId);

        Optional<Customer> CustomerById = CustomerService.temp(CustomerId);

        if (CustomerById.isPresent()) {
            Customer Customer = CustomerById.get();

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

    @GetMapping("/findById2/{id}")
    public Customer findOneCustomer2(@PathVariable(name = "id") Long CustomerId) throws JsonProcessingException {
        Optional<Customer> CustomerById = CustomerService.findCustomerById2(CustomerId);

        if (CustomerById.isPresent()) {
            return CustomerById.get();
        } else {
            return null;
        }
    }

    @GetMapping("/findByEmail")
    public CustomeromerDetailDTO findOneCustomerByName(@RequestBody HashMap map) {
        String email = map.get("email").toString();
        log.info("email = {}", email);
        CustomeromerDetailDTO CustomeromerDetailDTO = CustomerService.findCustomerByEmail(email);

        if (CustomeromerDetailDTO.getId() != null) {
            return CustomeromerDetailDTO;
        } else {
            return null;
        }
    }

    @PostMapping("/update/{id}")
    public boolean CustomerUpdate(@PathVariable(name = "id") Long CustomerId,
                              @RequestBody CustomeromerUpdateDTO updateDTO) {
        boolean result = CustomerService.updateCustomerInfo(CustomerId, updateDTO);

        return result;
    }

}