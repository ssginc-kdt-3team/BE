package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public boolean CheckPw(OwnerChangePwDTO changePwDTO){

        String email = changePwDTO.getEmail();
        String in_Password = changePwDTO.getNowPassword();
        Optional<Owner> PwCheck = repo.findByEmail(email);

        Owner owner = PwCheck.get();
        String Rpassword = owner.getPassword();
        if (Rpassword.equals(in_Password)){
            return true;
        }else {
            return false;
        }
    }
    public void ChangePw(OwnerChangePwDTO changePwDTO) throws Exception{
        String pw1 = changePwDTO.getNewPassword1();
        String pw2 = changePwDTO.getNewPassword2();

        if (pw1 != pw2){
            throw new Exception();
        }
        repo.updatePassword(pw1);
    }
}
