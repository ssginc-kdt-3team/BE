package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerFindPwDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;


import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor


public class OwnerFindPwService{
    @Autowired
    private final JpaDataOwnerRepository repo;

    public void findPw(OwnerFindPwDTO ownerFindPwDTO) throws Exception{

        String email = ownerFindPwDTO.getEmail();
        String name = ownerFindPwDTO.getName();
        String phone = ownerFindPwDTO.getPhone();

        boolean emailCheck = repo.existsByEmail(email);

        if(!emailCheck){
            throw new Exception("존재하지 않는 회원입니다.");
        }

        Optional<Owner> InfoOwner = repo.findByEmail(email);

        Owner EmaillMat = InfoOwner.get();

        String RepoName = EmaillMat.getName();
        String RepoPhone = EmaillMat.getPhoneNumber();
        //Repo에서 넘어온 email이 속해있는 레코드의 필드 값들
        //email과 같은 레코드에 있는 name,phone인지 확인

        if (!RepoName.equals(name)){
            throw new Exception("이름이 다릅니다.");

        }
        else if(!RepoPhone.equals(phone)){
            throw new Exception("전화번호가 다릅니다.");
        }
    }
}
