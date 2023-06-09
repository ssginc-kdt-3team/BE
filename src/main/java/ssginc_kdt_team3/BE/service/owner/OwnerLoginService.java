package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerLoginDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class OwnerLoginService {

    private final JpaDataOwnerRepository JpaRepo;

    public Map<String,Object> loginCheck(OwnerLoginDTO login) throws Exception {

        String email = login.getEmail();
        String pw = login.getPassword();
//        입력받은 거
        Optional<Owner> findEmail = JpaRepo.findByEmail(email);

        if (!findEmail.isPresent()){
            throw new IllegalStateException("이메일이 일치하지 않습니다!");
        }
        Owner owner = findEmail.get();

        if (!owner.getPassword().equals(pw)){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다!");
        }
        Map<String,Object> ownerIdAndName = new HashMap<>();

        ownerIdAndName.put("id",owner.getId());
        ownerIdAndName.put("name",owner.getName());

        return ownerIdAndName;
    }
}