package ssginc_kdt_team3.BE.repository.branch;

import org.springframework.data.jpa.repository.JpaRepository;
import ssginc_kdt_team3.BE.domain.Admin;
import ssginc_kdt_team3.BE.domain.Branch;

public interface JpaDataBranchRepository extends JpaRepository<Branch, Long> {
}
