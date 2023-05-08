package ssginc_kdt_team3.BE.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.cust.Address;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerUpdateDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;

import java.time.LocalDate;
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

    public boolean updateOwnerInfo(Long id, OwnerUpdateDTO updateDTO) {
        Optional<Owner> byId = ownerRepository.findById(id);

        if (byId.isPresent()) {
            Owner owner = byId.get();

            String name = updateDTO.getName();
            String phone = updateDTO.getPhone();
            String password = updateDTO.getPassword();
            LocalDate birthday = updateDTO.getBirthday();
            Address adddress = updateDTO.getAdddress();
            UserStatus userStatus = updateDTO.getUserStatus();

            owner.setName(name);
            owner.setPhoneNumber(phone);
            owner.setPassword(password);
            owner.setBirthday(birthday);
            owner.setAddress(adddress);
            owner.setStatus(userStatus);

            try {
                ownerRepository.save(owner);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }


}
