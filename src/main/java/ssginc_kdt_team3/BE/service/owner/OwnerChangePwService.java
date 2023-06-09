package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.owner.CheckPwDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerChangePwService{

    private final JpaDataOwnerRepository repo;

    public void CheckPw(CheckPwDTO checkPwDTO) throws Exception{

        String email = checkPwDTO.getEmail();
        String name = checkPwDTO.getName();
        String phone = checkPwDTO.getPhone();
        String in_Password = checkPwDTO.getPassword();

        Optional<Owner> ownerInfo = repo.findByEmail(email);

        if (!ownerInfo.isPresent()){
            throw new Exception("존재하지 않는 회원입니다!");
        }
        Owner owner = ownerInfo.get();

        String ownerName = owner.getName();
        String ownerPassword = owner.getPassword();
        String ownerPhone = owner.getPhoneNumber();
        if(!ownerName.equals(name) || !ownerPassword.equals(in_Password) || !ownerPhone.equals(phone)){
            throw new IllegalStateException("입력하신 정보를 다시 확인해주세요!");
        }
    }
}
