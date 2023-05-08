package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerLoginDTO;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class OwnerLoginService {
    @Autowired
    private final JpaDataOwnerRepository Repository;

    public String loginCheck(OwnerLoginDTO login) throws Exception {

        String email = login.getEmail();
        String pw = login.getPassword();
        //입력받은 거


        boolean checkEmail = Repository.existsByEmail(email);
        String CheckPassword = Repository.PasswordMatchEmail(email);

        if (!checkEmail) {
            throw new Exception("이메일이 일치하지 않습니다!");
        } else if (!pw.equals(CheckPassword)) {
            throw new Exception("비밀번호가 일치하지 않습니다!");
        }else {
            return email;
        }


    }

}