package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerFindPwDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerNewPwDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.owner.DataOwnerRepository;


import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor


public class OwnerFindPwService{

    private final DataOwnerRepository repo;

    private String email;

    public void findPw(OwnerFindPwDTO ownerFindPwDTO) throws Exception{

        this.email = ownerFindPwDTO.getEmail();
        String name = ownerFindPwDTO.getName();
        String phone = ownerFindPwDTO.getPhone();

        boolean emailCheck = repo.existsEmail(this.email);

        if(!emailCheck){
            throw new Exception("존재하지 않는 회원입니다.");
        }

        Optional<Owner> InfoOwner = repo.findByEmail(email);

        Owner EmaillMat = InfoOwner.get();

        String RepoName = EmaillMat.getName();
        String RepoPhone = EmaillMat.getPhoneNumber();
//        Repo에서 넘어온 email이 속해있는 레코드의 필드 값들
//        email과 같은 레코드에 있는 name,phone인지 확인

        if (!RepoName.equals(name)){
            throw new Exception("이름이 다릅니다.");

        }
        else if(!RepoPhone.equals(phone)){
            throw new Exception("전화번호가 다릅니다.");
        }
    }
    public void NewPw(OwnerNewPwDTO newPwDTO) throws Exception {
        String pw1 = newPwDTO.getNewPassword1();
        String pw2 = newPwDTO.getNewPassword2();

        if (!pw1.equals(pw2)) {
            throw new Exception("비밀번호가 서로 다릅니다!");
        }else {
            repo.updatePassword(this.email,pw1);
        }
    }


}
