package ssginc_kdt_team3.BE.service.branch;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ssginc_kdt_team3.BE.DTOs.branch.BranchShopDTO;
import ssginc_kdt_team3.BE.repository.branch.BranchRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchShopListService {

    private final BranchRepository repo;

    public List<BranchShopDTO> BranchShop(Long id) throws Exception {

        try {
            List<BranchShopDTO> BranchShopAll = repo.BranchShopList(id);

            if (!BranchShopAll.isEmpty()) {
                return BranchShopAll;
            }
            else{
                throw new Exception();
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID와 일치하는 지점이 존재하지 않습니다!");
        }

    }
}
