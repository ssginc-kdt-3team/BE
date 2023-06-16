package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerFindPwDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerNewPwDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.owner.DataOwnerRepository;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;


import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor


public class OwnerFindPwService{

    private final JpaDataOwnerRepository repo;

    public void findPw(OwnerFindPwDTO ownerFindPwDTO){

        String email = ownerFindPwDTO.getEmail();
        String name = ownerFindPwDTO.getName();
        String phone = ownerFindPwDTO.getPhone();

        Optional<Owner> owner = repo.findByEmail(email);

        if(!owner.isPresent()){
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
        Owner ownerInfo = owner.get();
        String ownerName = ownerInfo.getName();
        String ownerPhone = ownerInfo.getPhoneNumber();

        if (!ownerName.equals(name) || !ownerPhone.equals(phone)) {
            throw new IllegalStateException("이름 또는 전화번호를 다시 확인해주세요!");
        }
    }
    public void NewPw(OwnerNewPwDTO newPwDTO,Long ownerId) throws Exception {
        String pw1 = newPwDTO.getNewPassword1();
        String pw2 = newPwDTO.getNewPassword2();

        if (!pw1.equals(pw2)) {
            throw new Exception("비밀번호가 서로 다릅니다!");
        }
            repo.ownerNewPassword(pw1,ownerId);
    }

}
