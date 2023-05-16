package ssginc_kdt_team3.BE.repository.owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.DTOs.customer.Address;
import ssginc_kdt_team3.BE.enums.UserStatus;

import java.util.Optional;

public interface JpaDataOwnerRepository extends JpaRepository<Owner, Long> {

    boolean existsByEmail(String email);

//    @Query("SELECT o.password FROM Owner o WHERE o.email = :email")
//    String PasswordMatchEmail(@Param("email")String email);

    Optional<Owner> findByEmail(String email);

    Page<Owner> findAllBy(Pageable pageable);


    //    @Query("UPDATE Owner o SET o.password = :password")
//    @Modifying
//    void updatePassword(@Param("password") String password);


    //실행 X
//    @Query("UPDATE Owner o SET o.phoneNumber = :phoneNumber,o.address = :address,o.status = :status")
//    @Modifying
//    void updateOwnerInfo(@Param("phoneNumber")String phone,@Param("address") Address address,@Param("status") UserStatus status);
//        void updateOwnerInfo(Owner owner);
    //실행 X
//    boolean checkPassword(String password);
    //실행 X
//    boolean CheckPassword(String password);
    //실행 X
//    Optional<Owner> findByPassword(String password);

//    String findNameByEmail(String email);
    //실행 X
//    Optional<Owner> findByPw(String name, String email,String phone);
    //실행 X
//    boolean OwnerInfoCheck(String name, String email, String phone);
}

