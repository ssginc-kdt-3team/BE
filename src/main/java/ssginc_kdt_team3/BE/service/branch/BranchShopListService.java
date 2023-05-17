package ssginc_kdt_team3.BE.service.branch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.DTOs.branch.BranchShopDTO;
import ssginc_kdt_team3.BE.repository.branch.BranchRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchShopListService {

    private final BranchRepository repo;

    public String BranchShop(long id) throws Exception {

        List<BranchShopDTO> BranchShopAll = repo.BranchShopList(id);

        if (BranchShopAll.isEmpty()) {
            throw new Exception("Not Found Branch ID!!");
        }
     String resultJson = ConvertJson(BranchShopAll);
        return resultJson;
    }

    public String ConvertJson(List<BranchShopDTO> list) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(list);
    }

}
