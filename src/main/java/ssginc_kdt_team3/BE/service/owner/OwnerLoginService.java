package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private final JpaDataOwnerRepository Repository;

    public Owner loginCheck(OwnerLoginDTO login) {

        String email = login.getEmail();
        String pw = login.getPassword();

        Optional<Owner> byIdAndPw = Repository.findByIdAndPw(email, pw);

        return byIdAndPw.orElse(null);

    }

}