package ssginc_kdt_team3.BE.controller.branch;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssginc_kdt_team3.BE.DTOs.branch.BranchDTO;
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
}
