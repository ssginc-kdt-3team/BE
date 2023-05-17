package ssginc_kdt_team3.BE.service.admin.branch;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.branch.BranchDTO;
import ssginc_kdt_team3.BE.DTOs.shop.CustomerShopListDTO;
import ssginc_kdt_team3.BE.domain.Branch;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.repository.branch.BranchRepository;
import ssginc_kdt_team3.BE.repository.shop.JpaDataShopRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BranchService {
  private final BranchRepository branchRepository;
  private final JpaDataShopRepository shopRepository;

  // 지점 조회
  public Branch getBranch(Long id) {
    return branchRepository.findBranch(id);
  }

  // 전체 지점조회
  public List<BranchDTO> getAllBranch() {
    List<Branch> allBranch = branchRepository.findAllBranch();

    //먼저 선언 하고 -> for문 값을 넣어줘야 돼
    List<BranchDTO> branchDTOList = new ArrayList<>();

    for (Branch b : allBranch) {
      Long id = b.getId();
      String name = b.getName();
      BranchDTO branchDTO = new BranchDTO();
      branchDTO.setId(id);
      branchDTO.setName(name);

      branchDTOList.add(branchDTO);
    }

    return branchDTOList;
  }

  public List<CustomerShopListDTO> showShopOfBranch(Long id) {
    List<Shop> branchShop = shopRepository.findBranchShop(id);
    List<CustomerShopListDTO> shops = new ArrayList<>();
    for (Shop shop : branchShop) {
      CustomerShopListDTO dto = new CustomerShopListDTO(shop);
      shops.add(dto);
    }

    return shops;
  }

}
