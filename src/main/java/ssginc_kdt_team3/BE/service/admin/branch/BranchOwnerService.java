package ssginc_kdt_team3.BE.service.admin.branch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ssginc_kdt_team3.BE.DTOs.admin.AdminBranchOwnerDTO;
import ssginc_kdt_team3.BE.domain.Branch;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BranchOwnerService {

    private final JpaDataOwnerRepository repository;

    public Page<AdminBranchOwnerDTO> branchMatchOwner(Long id, Pageable pageable) throws Exception{

        Page<AdminBranchOwnerDTO> BranchIdMatchList = repository.BranchIdFindShopWithOwner(id,pageable);

        try {
            if (!BranchIdMatchList.isEmpty()) {

                return BranchIdMatchList;

            } else {
                throw new Exception("해당 ID와 일치하는 매장이 없습니다!");
            }
        }
        catch(Exception e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
            }




//        List<AdminBranchOwnerDTO> objectList = new ArrayList<>();
//
//        for (Object[] object : BranchIdMatchList) {
//
//            AdminBranchOwnerDTO abo = new AdminBranchOwnerDTO();
//
//            Shop shop = (Shop)object[0];
//            abo.setShopName(shop.getName());
//            abo.setShopLocation(shop.getLocation());
//
//            Owner owner = (Owner)object[1];
//            abo.setOwnerId(owner.getId());
//            abo.setOwnerName(owner.getName());
//            abo.setOwnerPhone(owner.getPhoneNumber());
//
//            Branch branch = (Branch)object[2];
//            abo.setBranchName(branch.getName());
//
//            objectList.add(abo);
//    }
//        Page<AdminBranchOwnerDTO> BranchByOwnersList = new PageImpl<>(objectList,pageable, objectList.size());

    }

}
