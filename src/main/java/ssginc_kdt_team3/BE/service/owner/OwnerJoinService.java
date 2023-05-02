package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class OwnerJoinService {

    private final JpaDataOwnerRepository jpaDataOwnerRepository;

    public void join(OwnerJoinDTO ownerJoinDTO){
        Owner owner = new Owner();

        owner.setEmail(ownerJoinDTO.getEmail());
        owner.setPassword(ownerJoinDTO.getPassword());
        owner.setName(ownerJoinDTO.getName());
        owner.setPhone(ownerJoinDTO.getPhone());
        owner.setBirthday(ownerJoinDTO.getBirthday());
        owner.setAddress(ownerJoinDTO.getAdddress());
        owner.setGender(ownerJoinDTO.isGender());

        jpaDataOwnerRepository.saveAll(owner);
    }

}