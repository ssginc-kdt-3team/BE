package ssginc_kdt_team3.BE.controller.branch;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.branch.BranchDTO;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerListDTO;
import ssginc_kdt_team3.BE.DTOs.deposit.AdminDepositDTO;
import ssginc_kdt_team3.BE.DTOs.shop.CustomerShopListDTO;
import ssginc_kdt_team3.BE.domain.Branch;
import ssginc_kdt_team3.BE.service.branch.BranchService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/branch")
public class BranchController {

  private final BranchService branchService;

  @RequestMapping("/all") //지점 전체 목록이랑 아이디랑 이름
  public List<BranchDTO> getAllBranch(BranchDTO branchDTO) {
    return branchService.getAllBranch();
  }

  @RequestMapping("/{id}")
  public Branch getBranch(@PathVariable Long id){
    return branchService.getBranch(id);
  }

  @GetMapping("/shops/{id}")
  public ResponseEntity<List<CustomerShopListDTO>> getShopOfBranch(@PathVariable(name = "id") Long id) {
    List<CustomerShopListDTO> customerShopListDTOS = branchService.showShopOfBranch(id);

    return ResponseEntity.ok(customerShopListDTOS);
  }
}
