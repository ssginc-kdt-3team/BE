package ssginc_kdt_team3.BE.repository.owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ssginc_kdt_team3.BE.domain.Owner;

import java.util.Optional;

@Repository
public interface JpaDataOwnerRepository extends JpaRepository<Owner, Long> {

    boolean existsByEmail(String email);

    Optional<Owner> findByEmail(String email);

    Page<Owner> findAllBy(Pageable pageable);

    @Query("UPDATE Owner o SET o.password = :password")
    @Modifying
    void updatePassword(@Param("password") String password);

//    boolean CheckPassword(String password);
//
//    Optional<Owner> findByPassword(String password);
//
//    String findNameByEmail(String email);
//
//    Optional<Owner> findByPw(String name, String email,String phone);

//    boolean OwnerInfoCheck(String name, String email, String phone);
}

