package ssginc_kdt_team3.BE.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.cust.*;
import ssginc_kdt_team3.BE.domain.Admin;
import ssginc_kdt_team3.BE.domain.Cust;
import ssginc_kdt_team3.BE.domain.Grade;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.cust.JpaDateCustRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminCustService {

    private final JpaDateCustRepository custRepository;

    public Page<CustListDTO> findAllCust(Pageable pageable) {
        return custRepository.findAllBy(pageable);
    }

    public CustDetailDTO findCustById(Long custId) {

        Optional<Cust> byId = custRepository.findById(custId);

        if (byId.isPresent()) {
            Cust cust = byId.get();
            CustDetailDTO custDetailDTO = new CustDetailDTO(cust.getId(), cust.getEmail(), cust.getPassword(),
                    cust.getName(), cust.getPhone(), cust.getGender(), cust.getBirthday(), cust.getAddress(),
                    cust.getRole(), cust.getStatus(), cust.getGrade().getName());

            return custDetailDTO;
        }


        return new CustDetailDTO();
    }

    public CustDetailDTO findCustByEmail(String custEmail) {
        Optional<Cust> byEmail = custRepository.findCustByEmail(custEmail);

        if (byEmail.isPresent()) {
            Cust cust = byEmail.get();
            CustDetailDTO custDetailDTO = new CustDetailDTO(cust.getId(), cust.getEmail(), cust.getPassword(),
                    cust.getName(), cust.getPhone(), cust.getGender(), cust.getBirthday(), cust.getAddress(),
                    cust.getRole(), cust.getStatus(), cust.getGrade().getName());

            return custDetailDTO;
        }

        return null;
    }

    public boolean updateCustInfo(Long custId, CustUpdateDTO custDTO) {

        Optional<Cust> cust = custRepository.findById(custId);

        String name = custDTO.getName();
        String password = custDTO.getPassword();
        String phone = custDTO.getPhone();
        Address address = custDTO.getAddress();
        UserStatus status = custDTO.getStatus();
        Grade grade = custDTO.getGrade();

        if (cust.isPresent()) {
            Cust findCust = cust.get();

            findCust.setPassword(password);
            findCust.setName(name);
            findCust.setPhone(phone);
            findCust.setAddress(address);
            findCust.setStatus(status);
            findCust.setGrade(grade);

            return true;
        } else {
            return false;
        }
    }

}
