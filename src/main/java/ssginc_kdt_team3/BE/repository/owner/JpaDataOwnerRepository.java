package ssginc_kdt_team3.BE.repository.owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssginc_kdt_team3.BE.DTOs.admin.AdminOwnerDetailDTO;
import ssginc_kdt_team3.BE.domain.Owner;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface JpaDataOwnerRepository extends JpaRepository<Owner, Long> {

    boolean existsByEmail(String email);

//    @Query("SELECT o.password FROM Owner o WHERE o.email = :email")
//    String PasswordMatchEmail(@Param("email")String email);

    Optional<Owner> findByEmail(String email);

    Page<Owner> findAllBy(Pageable pageable);

    @Query("SELECT s, o, b FROM Shop s " +
            "JOIN Owner o ON s.owner.id = o.id " +
            "JOIN Branch b ON s.branch.id = b.id " +
            "WHERE s.branch.id = :id")
    List<Object[]> BranchIdFindShopWithOwner(@Param("id") Long id);

    @Query("SELECT new ssginc_kdt_team3.BE.DTOs.admin.AdminOwnerDetailDTO(o.id, o.email, o.name, o.gender, o.address, o.phoneNumber, o.birthday, o.status, " +
            "s.name, s.location, " +
            "b.name) FROM Shop s " +
            "JOIN s.owner o " +
            "JOIN s.branch b " +
            "WHERE o.id = :id")
    Optional<AdminOwnerDetailDTO> OwnerIdFindByOwnerDetail(@Param("id") Long id);

}

