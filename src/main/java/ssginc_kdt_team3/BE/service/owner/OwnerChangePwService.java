package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.owner.CheckPwDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerChangePwDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.owner.DataOwnerRepository;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;


import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional

public class OwnerChangePwService{

    private final DataOwnerRepository repo;

//    private final JpaDataOwnerRepository repo2;

    private String email;

    public void CheckPw(CheckPwDTO checkPwDTO) throws Exception{

        this.email = checkPwDTO.getEmail();
        String name = checkPwDTO.getName();
        String in_Password = checkPwDTO.getPassword();

        Optional<Owner> PwCheck = repo.findByEmail(this.email);

        if (PwCheck.equals(Optional.empty())){
            throw new Exception("존재하지 않는 회원입니다!");
        }
        Owner owner = PwCheck.get();
        String Rpassword = owner.getPassword();
        String Rname = owner.getName();

        if (!Rpassword.equals(in_Password)) {
            throw new Exception("비밀번호가 일치하지 않습니다!");
        }
        else if(!Rname.equals(name)){
            throw new Exception("이름이 일치하지 않습니다!");
        }
    }

}
