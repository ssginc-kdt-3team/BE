package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.cust.Address;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerUpdateDTO;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;

@RequiredArgsConstructor
@Service
@Transactional
public class OwnerUpdateService {
    @Autowired
    private final JpaDataOwnerRepository repo;

    public void OwnerUpdate(OwnerUpdateDTO ownerUpdateDTO){

//        Owner owner = new Owner();

//        owner.setPhone(ownerUpdateDTO.getPhone());
//        owner.setAddress(ownerUpdateDTO.getAdddress());
//        owner.setStatus(ownerUpdateDTO.getUserStatus());

        String bePhone = ownerUpdateDTO.getPhone();
        Address beAddress = ownerUpdateDTO.getAdddress();
        UserStatus beStatus = ownerUpdateDTO.getUserStatus();

        repo.updateOwnerInfo(bePhone,beAddress,beStatus);
//        repo.updateOwnerInfo(owner);


    }
}
