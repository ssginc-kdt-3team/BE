package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerLoginDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;

import javax.transaction.Transactional;
import java.util.List;
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

        Optional<Owner> OwnerEmail = Repository.findByEmail(email);

        if(OwnerEmail.isEmpty()){
            throw new Exception("아이디 또는 비밀번호를 확인해주세요.");
        }


        Owner EmailMat = OwnerEmail.get();
        String OwnerPassword = EmailMat.getPassword();
        String OwnerName = EmailMat.getName();

        if(OwnerPassword != pw){
            throw new Exception("아이디 혹은 비밀번호를 확인해주세요.");
        }
        else {
            return OwnerName;
        }
    }

}