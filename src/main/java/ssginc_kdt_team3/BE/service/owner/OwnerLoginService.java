package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerLoginDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.owner.DataOwnerRepository;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class OwnerLoginService {

    private final DataOwnerRepository Repo;


    public Long loginCheck(OwnerLoginDTO login) throws Exception {

        String email = login.getEmail();
        String pw = login.getPassword();
//        입력받은 거


        boolean checkEmail = Repo.existsEmail(email);
        String CheckPassword = Repo.PasswordMatchEmail(email);

        log.info(CheckPassword);

        if (!checkEmail) {
            throw new Exception("이메일이 일치하지 않습니다!");
        } else if (!pw.equals(CheckPassword)) {
            throw new Exception("비밀번호가 일치하지 않습니다!");
        }else {
            Optional<Owner> byEmail = Repo.findByEmail(email);
            long id = byEmail.get().getId();
            return id;
        }

    }

}