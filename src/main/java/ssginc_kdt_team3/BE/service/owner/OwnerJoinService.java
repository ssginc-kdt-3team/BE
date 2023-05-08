package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class OwnerJoinService {
    @Autowired
    private final JpaDataOwnerRepository jpaDataOwnerRepository;

    public void join(OwnerJoinDTO ownerJoinDTO) throws Exception {
        Owner owner = new Owner();

        owner.setEmail(ownerJoinDTO.getEmail());
        owner.setPassword(ownerJoinDTO.getPassword());
        owner.setName(ownerJoinDTO.getName());
        owner.setPhone(ownerJoinDTO.getPhone());
        owner.setBirthday(ownerJoinDTO.getBirthday());
        owner.setAddress(ownerJoinDTO.getAdddress());
        owner.setGender(ownerJoinDTO.isGender());

        if(jpaDataOwnerRepository.existsByEmail(owner.getEmail())){
            throw new Exception("중복된 이메일 입니다.");
        }else{
            jpaDataOwnerRepository.save(owner);
        }
    }

}