package ssginc_kdt_team3.BE.controller.customer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.deposit.AdminDepositDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.CustomerReservationAddDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.CustomerReservationListDTO;
import ssginc_kdt_team3.BE.repository.deposit.CustomerDepositRepository;
import ssginc_kdt_team3.BE.service.customer.CustomerReservationService;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/reservation")
public class CustomerReservationController {

    private final CustomerReservationService reservationService;

    @PostMapping("/add")
    public ResponseEntity showDepositList(@Validated @RequestBody CustomerReservationAddDTO dto, BindingResult bindingResult) {

        if (dto.getPeople() <= dto.getChild()) {
            log.info("too many baby");
            bindingResult.reject("too many baby");
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        boolean b = reservationService.makeReservation(dto);

        if (b) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().body("hello");
    }

    @GetMapping("listAll/{id}")
    public ResponseEntity<Page<CustomerReservationListDTO>> showAllCustomerReservation(@PathVariable(name = "id") Long id) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CustomerReservationListDTO> list = reservationService.showMyAllReservation(pageable, id);

        return ResponseEntity.ok(list);
    }

    @GetMapping("listActive/{id}")
    public ResponseEntity<Page<CustomerReservationListDTO>> showActiveCustomerReservation(@PathVariable(name = "id") Long id) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CustomerReservationListDTO> list = reservationService.showMyActiveReservation(pageable, id);

        return ResponseEntity.ok(list);
    }
}
