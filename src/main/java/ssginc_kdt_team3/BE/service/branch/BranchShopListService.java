//package ssginc_kdt_team3.BE.service.branch;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.stereotype.Service;
//import ssginc_kdt_team3.BE.DTOs.branch.BranchShopDTO;
//import ssginc_kdt_team3.BE.domain.Shop;
//import ssginc_kdt_team3.BE.repository.branch.BranchRepository;
//import ssginc_kdt_team3.BE.repository.branch.JPABranchShopRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class BranchShopListService {
//
//    private final JPABranchShopRepository repo;
//
//    public BranchShopListService(JPABranchShopRepository repo) {
//        this.repo = repo;
//    }
//
//    public String BranchShop(long id) throws Exception {
//
//        Optional<Shop> BranchShopAll = repo.findByBranchId(id);
//
//        if (BranchShopAll.isEmpty()) {
//            throw new Exception("Not Found Branch ID!!");
//        }
//        Shop ShopList = BranchShopAll.get();
//
//        BranchShopDTO branchShopDTO = new
//
//
//    }
//    public List<BranchShopDTO> repeat(List<BranchShopDTO> result, List<Shop> shops){
//
//    }
//
//    public String ConvertJson(List<BranchShopDTO> list) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.writeValueAsString(list);
//    }
//
//}
