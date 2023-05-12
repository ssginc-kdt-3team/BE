package ssginc_kdt_team3.BE.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import ssginc_kdt_team3.BE.domain.Admin;

import java.util.Optional;

public interface JpaDateAdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByLoginId(String loginId);
}
