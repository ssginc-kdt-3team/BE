package ssginc_kdt_team3.BE.controller.cust;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ssginc_kdt_team3.BE.DTOs.cust.CustJoinDTO;
import ssginc_kdt_team3.BE.DTOs.cust.CustLoginDTO;
import ssginc_kdt_team3.BE.service.cust.CustService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cust")
public class CustController {
  private final CustService custService;

  @RequestMapping("/join")
  public CustJoinDTO join(CustJoinDTO custJoinDTO){
    CustJoinDTO join = custService.join(custJoinDTO);
    return custJoinDTO;
  }

  @RequestMapping("/login")
  public CustLoginDTO login(CustLoginDTO custLoginDTO) {
    CustLoginDTO loginCust = custService.login(custLoginDTO);
    return custLoginDTO;
  }

}

