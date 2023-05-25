package ssginc_kdt_team3.BE.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ssginc_kdt_team3.BE.DTOs.admin.AdminReviewListDTO;
import ssginc_kdt_team3.BE.domain.Admin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JpaDateAdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByLoginId(String loginId);

}
