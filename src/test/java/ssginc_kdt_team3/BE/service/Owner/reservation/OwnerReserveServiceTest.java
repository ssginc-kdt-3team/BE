package ssginc_kdt_team3.BE.service.Owner.reservation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.reservation.OwnerReserveDTO;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.repository.customer.JpaCustomerRepository;
import ssginc_kdt_team3.BE.repository.owner.reservation.OwnerRepository;
import ssginc_kdt_team3.BE.service.customer.CustomerService;
import ssginc_kdt_team3.BE.service.owner.reservation.OwnerReserveService;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
class OwnerReserveServiceTest {

  @Autowired
  OwnerReserveService ownerReserveService;
  @Autowired
  JpaCustomerRepository customerRepository;

  @Autowired
  CustomerService customerService;
//  @Test
//  void 모든_예약내역_조회() {
//    Optional<Customer> customer = customerRepository.findCustomer(2L);
//
//    List<OwnerReserveDTO> allReserve = ownerReserveService.getAllReserve();
//    System.out.println("allReserve ==========>" +allReserve);
//
//  }
}