package ssginc_kdt_team3.BE.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerListDTO;
import ssginc_kdt_team3.BE.DTOs.deposit.AdminDepositDTO;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.repository.customer.JpaDateCustomerRepository;
import ssginc_kdt_team3.BE.repository.deposit.AdminDepositRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminDepositService {

    private final AdminDepositRepository depositRepository;

    public Optional<Page<AdminDepositDTO>> findDepositList(Pageable pageable, String type, Long id) {

        if (type.equals("branch")) {
            List<Deposit> branchDepositList = depositRepository.findBranchDepositList(id);
            return Optional.ofNullable(listToDTOPage(pageable, branchDepositList));
        } else if (type.equals("shop")) {
            List<Deposit> shopDepositList = depositRepository.findShopDepositList(id);
            return Optional.ofNullable(listToDTOPage(pageable, shopDepositList));
        }

        return Optional.ofNullable(null);
    }

    private static Page<AdminDepositDTO> listToDTOPage(Pageable pageable, List<Deposit> branchDepositList) {
        List<AdminDepositDTO> depositDTOList = new ArrayList<>();

        for ( Deposit d : branchDepositList) {
            AdminDepositDTO ad = new AdminDepositDTO(d);
            depositDTOList.add(ad);

            log.info(ad.toString());
        }

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), depositDTOList.size());

        Page<AdminDepositDTO> adminDepositPage = new PageImpl<>(depositDTOList.subList(start, end), pageable, depositDTOList.size());

        return adminDepositPage;
    }
}
