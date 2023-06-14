package ssginc_kdt_team3.BE.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.deposit.AdminDepositDTO;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.repository.deposit.DepositRepository;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminDepositService {

    private final DepositRepository depositRepository;

    public Optional<Page<AdminDepositDTO>> findBranchDepositList(Pageable pageable, String type, Long id) {

        if (type.equals("branch")) {
            Page<Deposit> branchDepositList = depositRepository.findDepositByReservation_Shop_BranchId(id, pageable);

            Page<AdminDepositDTO> dtoPage = toDtoList(branchDepositList);

            return Optional.ofNullable(dtoPage);
        }

        return Optional.empty();
    }

    public Optional<Page<AdminDepositDTO>> findShopDepositList(Pageable pageable, String type, Long id) {

        if (type.equals("shop")) {
            Page<Deposit> shopDepositList = depositRepository.findDepositByReservation_Shop_Id(id, pageable);

            Page<AdminDepositDTO> dtoPage = toDtoList(shopDepositList);

            return Optional.ofNullable(dtoPage);
        }

        return Optional.ofNullable(null);
    }

    private Page<AdminDepositDTO> toDtoList(Page<Deposit> depositPage){
        Page<AdminDepositDTO> dtoPage = depositPage.map(m -> AdminDepositDTO.builder()
                .reservationId(m.getReservation().getId())
                .customerId(m.getReservation().getCustomer().getId())
                .customerName(m.getReservation().getCustomer().getName())
                .originDeposit(m.getOrigin_value())
                .payDeposit(m.getPayValue())
                .penalty(m.getPenaltyValue())
                .expectedDay(m.getReservation().getReservationDate().toLocalDate())
                .expectedTime(m.getReservation().getReservationDate().toLocalTime())
                .status(m.getStatus())
                .build());

        return dtoPage;
    }
}
