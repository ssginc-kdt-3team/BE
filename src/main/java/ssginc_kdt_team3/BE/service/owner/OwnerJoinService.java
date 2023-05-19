package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.owner.DataOwnerRepository;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class OwnerJoinService {

    private final JpaDataOwnerRepository repo;

    private final DataOwnerRepository repo2;

    public void join(OwnerJoinDTO ownerJoinDTO) throws Exception {

        Owner owner = new Owner();

        owner.setEmail(ownerJoinDTO.getEmail());
        owner.setPassword(ownerJoinDTO.getPassword());
        owner.setName(ownerJoinDTO.getName());
        owner.setPhoneNumber(ownerJoinDTO.getPhone());
        owner.setGender(ownerJoinDTO.isGender());
        owner.setAddress(ownerJoinDTO.getAdddress());

        owner.setBirthday(ownerJoinDTO.getBirthday());

        owner.setRole(UserRole.OWNER);
        owner.setStatus(UserStatus.ACTIVE);

        System.out.println(owner.toString());

        if(!repo2.existsEmail(owner.getEmail())){
            System.out.println("owner.getEmail()>>>>>>>>>>>>>"+owner.getEmail());
            throw new Exception("중복된 이메일 입니다.");
        }else{
            System.out.println("owner.toString(2)>>>>>>>>>>>>>"+owner.toString());
            repo.save(owner);
        }
    }

}