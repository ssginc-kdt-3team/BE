package ssginc_kdt_team3.BE.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.cust.Address;
import ssginc_kdt_team3.BE.DTOs.cust.CustListDTO;
import ssginc_kdt_team3.BE.DTOs.cust.InfoUpdateDTO;
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

    public List<CustListDTO> findAllCust() {
        List<CustListDTO> result = new ArrayList<>();

        custRepository.findAll().forEach(x ->
                result.add(new CustListDTO(x.getId(), x.getName(), x.getEmail(), x.getStatus(), x.getGrade())));

        return result;
    }

    public Optional<Cust> findCustById(Long custId) {
        return custRepository.findById(custId);
    }

    public Optional<Cust> findCustByName(String custName) {
        return custRepository.findCustByName(custName);
    }

    public boolean updateCustInfo(Long custId, InfoUpdateDTO custDTO) {

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
