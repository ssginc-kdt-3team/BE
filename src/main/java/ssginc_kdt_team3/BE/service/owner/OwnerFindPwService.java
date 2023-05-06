package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerFindPwDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;

import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor


public class OwnerFindPwService{
    @Autowired
    private final JpaDataOwnerRepository repo;

    public void findPw(OwnerFindPwDTO ownerFindPwDTO) throws Exception{
        Owner owner = new Owner();
        //owner객체 생성

        owner.setEmail(ownerFindPwDTO.getEmail());
        owner.setName(ownerFindPwDTO.getName());
        owner.setPhone(ownerFindPwDTO.getPhone());
        //생성한 owner객체에 값으로 이메일,이름,전화번호 설정

        String name = owner.getName();
        String email = owner.getEmail();
        String phone = owner.getPhone();
        //name,email,phone변수 생성하고 객체의 값 저장

        boolean emailCheck = repo.existsByEmail(email);
        //repository에서 입력받은 이메일이 DB에 존재하는지 확인
        //없을 경우 emailCheck에는 false 존재할 경우 true
        Optional<Owner> InfoOwner = repo.findByEmail(email);

        Owner EmaillMat = InfoOwner.get();

        String RepoName = EmaillMat.getName();
        String RepoPhone = EmaillMat.getPhone();
        //Repo에서 넘어온 email이 속해있는 레코드의 필드 값들
        //email과 같은 레코드에 있는 name,phone인지 확인

        if(!emailCheck){
            throw new Exception("존재하지 않는 회원입니다.");
        } else if (!RepoName.equals(name)){
            throw new Exception("이름이 다릅니다.");

        }
        else if(!RepoPhone.equals(phone)){
            throw new Exception("전화번호가 다릅니다.");
        }
    }

    public String TemporaryPw(){
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+";
        StringBuilder TempPw = new StringBuilder();
        Random random = new Random();
        int length = 10;
        for(int i = 0; i < length;i++){
            int index = random.nextInt(alphabet.length());
            TempPw.append(alphabet.charAt(index));
        }

        String TempPassword = TempPw.toString();
        repo.updatePassword(TempPassword);

        return TempPassword;
    }
}
