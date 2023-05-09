package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.owner.CheckPwDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerChangePwDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;


import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional

public class OwnerChangePwService{

    @Autowired
    private final JpaDataOwnerRepository repo;

    public void CheckPw(CheckPwDTO checkPwDTO) throws Exception{

        String email = checkPwDTO.getEmail();
        String name = checkPwDTO.getName();
        String in_Password = checkPwDTO.getPassword();

        Optional<Owner> PwCheck = repo.findByEmail(email);
        Owner owner = PwCheck.get();
        String Rpassword = owner.getPassword();
        String Rname = owner.getName();

        boolean emailCheck = repo.existsByEmail(email);
        if(!emailCheck){
            throw new Exception("존재하지 않는 회원입니다!");
        }
        else if (!Rpassword.equals(in_Password)) {
            throw new Exception("비밀번호가 일치하지 않습니다!");
        }
        else if(!Rname.equals(name)){
            throw new Exception("이름이 일치하지 않습니다!");
        }
    }
//    public void ChangePw(OwnerChangePwDTO changePwDTO) throws Exception{
//        String pw1 = changePwDTO.getNewPassword1();
//        String pw2 = changePwDTO.getNewPassword2();
//
//        if (!pw1.equals(pw2)){
//            throw new Exception("비밀번호가 서로 일치하지 않습니다!");
//        }
//        repo.updatePassword(pw1);
//    }
}
