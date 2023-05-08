package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerNewPwDTO;
//import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class OwnerNewPwService {
//    @Autowired
//    private final JpaDataOwnerRepository repo;
//    public void NewPw(OwnerNewPwDTO newPwDTO) throws Exception{
//        String pw1 = newPwDTO.getNewPassword1();
//        String pw2 = newPwDTO.getNewPassword2();
//
//        if (pw1 != pw2){
//            throw new Exception("비밀번호가 서로 다릅니다!");
//        }
//        repo.updatePassword(pw1);
//    }
}
