package ssginc_kdt_team3.BE.service.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.reservation.CustomerReservationAddDTO;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.repository.customer.JpaCustomerRepository;
import ssginc_kdt_team3.BE.repository.owner.shop.JpaDateShopRepository;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomerReservationService {

    private final JpaDataReservationRepository reservationRepository;
    private final JpaDateShopRepository shopRepository;
    private final JpaCustomerRepository customerRepository;


    public boolean makeReservation(CustomerReservationAddDTO dto) {
        Reservation reservation = new Reservation();

        Shop shop = shopRepository.findById(dto.getShopId()).get();
        Customer customer = customerRepository.findCustomer(dto.getUserId()).get();

        dto.setReservationInfo(reservation, shop, customer);
        log.info("user ========= {}", reservation.getCustomer().getEmail());
        reservationRepository.save(reservation);

        return true;
    }

}
