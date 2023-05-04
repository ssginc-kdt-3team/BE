package ssginc_kdt_team3.BE.service.admin;

import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.cust.Address;
import ssginc_kdt_team3.BE.DTOs.cust.CustListDTO;
import ssginc_kdt_team3.BE.DTOs.cust.CustUpdateDTO;
import ssginc_kdt_team3.BE.domain.Cust;
import ssginc_kdt_team3.BE.domain.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ssginc_kdt_team3.BE.DTOs.cust.*;
import ssginc_kdt_team3.BE.domain.QCust;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.cust.JpaDateCustRepository;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminCustService {

    private final JpaDateCustRepository custRepository;
//    private final EntityManager em;

    JPQLQueryFactory queryFactory;


    public Page<CustListDTO> findAllCust(Pageable pageable) {
        return custRepository.findAllBy(pageable);
    }

    public Optional<Cust> temp(Long custId) {
        return custRepository.findById(custId);
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

    public Optional<Cust> findCustById2(Long custId) {
        QCust cust = new QCust("cust");

        Optional<Cust> findCust= Optional.of(queryFactory
                .select(cust)
                .from(cust)
                .where(cust.id.eq(custId))
                .fetchOne());

        return findCust;
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