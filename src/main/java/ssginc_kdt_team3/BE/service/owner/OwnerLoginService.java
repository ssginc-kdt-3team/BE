package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerLoginDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class OwnerLoginService {

    private final JpaDataOwnerRepository Repository;
    public void loginCheck(OwnerLoginDTO login){

        String email = login.getEmail();
        String pw = login.getPassword();

        Optional<Owner> byIdAndPw = Repository.findByIdAndPw(email, pw);

        if (byIdAndPw.equals(email) && byIdAndPw.equals(pw)){
            System.out.println("로그인 성공");
        } else
            System.out.println("아이디와 비밀번호를 다시 확인해주세요.");
    }

}

