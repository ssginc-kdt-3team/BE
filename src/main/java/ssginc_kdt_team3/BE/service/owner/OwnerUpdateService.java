//package ssginc_kdt_team3.BE.service.owner;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import ssginc_kdt_team3.BE.DTOs.customer.Address;
//import ssginc_kdt_team3.BE.DTOs.owner.OwnerUpdateDTO;
//import ssginc_kdt_team3.BE.domain.Owner;
//import ssginc_kdt_team3.BE.enums.UserStatus;
//import ssginc_kdt_team3.BE.repository.owner.DataOwnerRepository;
//
//@RequiredArgsConstructor
//@Service
//
//public class OwnerUpdateService {
//
//    private final DataOwnerRepository repo;
//
//    public void OwnerUpdate(long id,OwnerUpdateDTO ownerUpdateDTO){
//
//        Owner owner = new Owner();
//
//        owner.setPhoneNumber(ownerUpdateDTO.getPhone());
//        owner.setAddress(ownerUpdateDTO.getAdddress());
//        owner.setName(ownerUpdateDTO.getName());
//
//        repo.updateOwnerInfo(owner,id);
//
//
//    }
//}
