package ssginc_kdt_team3.BE.service.admin.branch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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

    public Page<AdminBranchOwnerDTO> branchMatchOwner(Long id, Pageable pageable){

        List<Object[]> BranchIdMatchList = repository.BranchIdFindShopWithOwner(id);

        if (BranchIdMatchList.isEmpty()){
            throw new NoResultException("ID를 다시 입력해주세요!");
        }

        List<AdminBranchOwnerDTO> objectList = new ArrayList<>();

        for (Object[] object : BranchIdMatchList) {

            AdminBranchOwnerDTO abo = new AdminBranchOwnerDTO();

            Shop shop = (Shop)object[0];
            abo.setShopName(shop.getName());
            abo.setShopLocation(shop.getLocation());

            Owner owner = (Owner)object[1];
            abo.setOwnerId(owner.getId());
            abo.setOwnerName(owner.getName());
            abo.setOwnerPhone(owner.getPhoneNumber());

            Branch branch = (Branch)object[2];
            abo.setBranchName(branch.getName());

            objectList.add(abo);
    }
        Page<AdminBranchOwnerDTO> BranchByOwnersList = new PageImpl<>(objectList,pageable, objectList.size());

        return BranchByOwnersList;
    }

}
