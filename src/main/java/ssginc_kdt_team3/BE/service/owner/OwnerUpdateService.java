package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.cust.Address;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerUpdateDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;

@RequiredArgsConstructor
@Service
@Transactional
public class OwnerUpdateService {
    @Autowired
    private final JpaDataOwnerRepository repo;

    public void OwnerUpdate(OwnerUpdateDTO ownerUpdateDTO){

        OwnerUpdateDTO update = new OwnerUpdateDTO();

        String bePhone = ownerUpdateDTO.getPhone();
        Address beAddress = ownerUpdateDTO.getAdddress();
        UserStatus beStatus = ownerUpdateDTO.getUserStatus();

        repo.updateOwnerInfo(bePhone,beAddress,beStatus);

        System.out.println("변경 후 전화번호 : " + bePhone);
        System.out.println("변경 후 주소 : " + beAddress.toString());
        System.out.println("변경 후 유저 상태 : " + beStatus);

    }
}
