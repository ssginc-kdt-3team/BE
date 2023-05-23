package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.customer.Address;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerUpdateDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.owner.DataOwnerRepository;

@RequiredArgsConstructor
@Service

public class OwnerUpdateService {

   private final DataOwnerRepository repo;

   public void OwnerUpdate(long id,OwnerUpdateDTO ownerUpdateDTO){

        boolean existence = ownerRepository.existsByid(id);

            if (!existence){
                throw new NoResultException("ID를 다시 입력해주세요!");
            }
            String name = updateDTO.getName();
            String phone = updateDTO.getPhone();
            String city = updateDTO.getCity();
            String district = updateDTO.getDistrict();
            String detail = updateDTO.getDetail();
            String zipCode = updateDTO.getZipCode();
     
            try{

                ownerRepository.updateByOwner(id,name,phone,city,district,detail,zipCode);

            }catch (Exception e){
                throw new DataIntegrityViolationException("업데이트 하려는 값이 잘못되었습니다!");

            }


   }
}
