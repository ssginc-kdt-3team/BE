package ssginc_kdt_team3.BE.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.cust.CustListDTO;
import ssginc_kdt_team3.BE.service.admin.AdminCustService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminCustController {

    @Autowired
    private final AdminCustService custService;

    @GetMapping("/findAllCUst")
    public List<CustListDTO> findAllCust() {

        return custService.findAllCust();
    }

}
