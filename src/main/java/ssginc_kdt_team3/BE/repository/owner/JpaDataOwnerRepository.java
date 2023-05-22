package ssginc_kdt_team3.BE.repository.owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ssginc_kdt_team3.BE.domain.Owner;


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

//    @Query("SELECT s FROM Owner Shop s " +
//            "JOIN Owner o ON s.user_id = o.user_id" +
//            "JOIN Branch b ON b.branch_id = s.branch_id " +
//            "WHERE s.branch_id = :id")
//   Optional<Page<Object>> BranchIdFindShopWithOwner(@Param("id") Long id , Pageable pageable);

}

