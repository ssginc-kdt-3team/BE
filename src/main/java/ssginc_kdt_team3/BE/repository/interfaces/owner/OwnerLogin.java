package ssginc_kdt_team3.BE.repository.interfaces.owner;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerLoginDTO;

public interface OwnerLogin extends CrudRepository<OwnerLoginDTO, Long> {
    OwnerLoginDTO findByEmailAndPw(String email,String password);
}
