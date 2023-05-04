package ssginc_kdt_team3.BE.repository.owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssginc_kdt_team3.BE.domain.Cust;
import ssginc_kdt_team3.BE.domain.Owner;

import java.util.List;
import java.util.Optional;


public interface JpaDataOwnerRepository extends JpaRepository<Owner, Long> {

    boolean existsByEmail(String email);

    Optional<Owner> findByEmail(String email);

//    boolean CheckEmail(String email);

//    boolean CheckPassword(String password);


//
//    Optional<Owner> findByPassword(String password);
//
//    String findNameByEmail(String email);
//
//    Optional<Owner> findByPw(String name, String email,String phone);

    Page<Owner> findAllBy(Pageable pageable);
}

