package ssginc_kdt_team3.BE.repository.branch;

import org.springframework.data.jpa.repository.JpaRepository;
import ssginc_kdt_team3.BE.domain.Branch;
import ssginc_kdt_team3.BE.domain.BranchOperationInfo;

public interface JpaDataBranchInfoRepository extends JpaRepository<BranchOperationInfo, Long> {
}
