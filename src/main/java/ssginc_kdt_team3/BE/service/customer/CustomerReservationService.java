package ssginc_kdt_team3.BE.service.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.deposit.AdminDepositDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.CustomerReservationAddDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.CustomerReservationListDTO;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.customer.JpaCustomerRepository;
import ssginc_kdt_team3.BE.repository.deposit.CustomerDepositRepository;
import ssginc_kdt_team3.BE.repository.owner.shop.JpaDateShopRepository;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomerReservationService {

    private final JpaDataReservationRepository reservationRepository;
    private final JpaDateShopRepository shopRepository;
    private final JpaCustomerRepository customerRepository;
    private final CustomerDepositRepository depositRepository;


    public boolean makeReservation(CustomerReservationAddDTO dto) {
        Reservation reservation = new Reservation();

        Shop shop = shopRepository.findById(dto.getShopId()).get();
        Customer customer = customerRepository.findCustomer(dto.getUserId()).get();

        //DTO 검증
        dto.setReservationInfo(reservation, shop, customer);
        Reservation save = reservationRepository.save(reservation);

        Deposit deposit = new Deposit();

        deposit.setReservation(save);
        deposit.setStatus(DepositStatus.RECEIVE);
        deposit.setOrigin_value(3000*(dto.getPeople()-dto.getChild()));

        depositRepository.save(deposit);

        return true;
    }

    public Page<CustomerReservationListDTO> showMyActiveReservation(Pageable pageable, Long id) {
        List<Reservation> allBy = reservationRepository.findAllActive(id, ReservationStatus.RESERVATION);
        Page<CustomerReservationListDTO> customerReservations = listToDTOList(pageable, allBy);

        return customerReservations;
    }

    public Page<CustomerReservationListDTO> showMyAllReservation(Pageable pageable, Long id) {
        List<Reservation> allBy = reservationRepository.findAllMy(id);
        Page<CustomerReservationListDTO> customerReservations = listToDTOList(pageable, allBy);

        return customerReservations;
    }



    private static Page<CustomerReservationListDTO> listToDTOList(Pageable pageable, List<Reservation> allBy) {
        List<CustomerReservationListDTO> customerReservationList = new ArrayList<>();

        for ( Reservation r : allBy) {
            CustomerReservationListDTO dto = new CustomerReservationListDTO(r);
            customerReservationList.add(dto);
        }

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), customerReservationList.size());

        Page<CustomerReservationListDTO> customerReservations = new PageImpl<>(customerReservationList.subList(start, end), pageable, customerReservationList.size());
        return customerReservations;
    }

}
