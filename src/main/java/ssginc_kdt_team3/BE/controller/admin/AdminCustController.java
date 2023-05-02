package ssginc_kdt_team3.BE.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.cust.CustListDTO;
import ssginc_kdt_team3.BE.DTOs.cust.CustUpdateDTO;
import ssginc_kdt_team3.BE.domain.Cust;
import ssginc_kdt_team3.BE.service.admin.AdminCustService;

import java.util.Optional;

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
    public Cust findOneCust(@PathVariable(name = "id") Long custId) {
        Optional<Cust> custById = custService.findCustById(custId);

        if (custById.isPresent()) {
            return custById.get();
        } else {
            return null;
        }
    }

    @GetMapping("/findCustByEmail/{email}")
    public Cust findOneCustByName(@PathVariable(name = "email") String email) {
        Optional<Cust> custByName = custService.findCustByEmail(email);

        if (custByName.isPresent()) {
            return custByName.get();
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
