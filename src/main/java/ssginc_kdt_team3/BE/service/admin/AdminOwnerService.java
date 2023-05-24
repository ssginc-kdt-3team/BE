package ssginc_kdt_team3.BE.service.admin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerUpdateDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
//import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
import javax.persistence.NoResultException;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminOwnerService {

    private final JpaDataOwnerRepository ownerRepository;

    public Page<Owner> findAllOwner(Pageable pageable) {
        return ownerRepository.findAllBy(pageable);
    }

    public Optional<Owner> findOne(Long id) {

        return ownerRepository.findById(id);
    }

    public void updateOwnerInfo(Long id, OwnerUpdateDTO updateDTO) {

        boolean existence = ownerRepository.existsByid(id);

            if (!existence){
                throw new NoResultException("ID를 다시 입력해주세요!");
            }
            String name = updateDTO.getName();
            String phone = updateDTO.getPhone();
            String city = updateDTO.getCity();
            String district = updateDTO.getDistrict();
            String detail = updateDTO.getDetail();
            String zipCode = updateDTO.getZipCode();


            try{

                ownerRepository.updateByOwner(id,name,phone,city,district,detail,zipCode);

            }catch (Exception e){
                throw new DataIntegrityViolationException("업데이트 하려는 값이 잘못되었습니다!");

            }

    }
    public void ownerViewsDelete(Long id){

        UserStatus status = UserStatus.QUIT;
        try {

            int updateRows = ownerRepository.ownerStatusDelete(id, status);

            if (updateRows < 1){
                throw new Exception("탈퇴에 실패하였습니다.");
            }
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }


}

