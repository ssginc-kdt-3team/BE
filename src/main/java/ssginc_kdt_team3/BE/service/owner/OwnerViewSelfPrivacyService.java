package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ssginc_kdt_team3.BE.DTOs.admin.AdminOwnerDetailDTO;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnerViewSelfPrivacyService {

    private final JpaDataOwnerRepository jpaDataOwnerRepository;

    public AdminOwnerDetailDTO ownerViewSelfDetail(Long id) throws Exception {

        try {
            Optional<AdminOwnerDetailDTO> ownerViewList = jpaDataOwnerRepository.OwnerIdFindByOwnerDetail(id);
            if (ownerViewList.isPresent()) {

                AdminOwnerDetailDTO ownerSelfDetailList = ownerViewList.get();
                return ownerSelfDetailList;
            } else {
                throw new Exception();
            }
        }
            catch(Exception e){

                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 ID와 일치하는 점주를 찾을수 없습니다!");
            }









    }

}
