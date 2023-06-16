package ssginc_kdt_team3.BE.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.DTOs.admin.AdminOwnerDetailDTO;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
import javax.persistence.NoResultException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminOwnerDetailService {

    private final JpaDataOwnerRepository repository;

    public AdminOwnerDetailDTO checkAdminOwnerDetail(Long id){

        Optional<AdminOwnerDetailDTO> OwnerDetail = repository.OwnerIdFindByOwnerDetail(id);

        log.info("log info = {}",OwnerDetail);

        if (!OwnerDetail.isPresent()){
            throw new NoResultException("해당 ID의 점주가 존재하지 않습니다!");
        }

        AdminOwnerDetailDTO CheckOwnerDetailDTO = OwnerDetail.get();

        return CheckOwnerDetailDTO;

    }
}
