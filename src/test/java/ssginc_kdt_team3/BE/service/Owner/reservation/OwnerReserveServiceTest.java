package ssginc_kdt_team3.BE.service.Owner.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.repository.customer.JpaCustomerRepository;
import ssginc_kdt_team3.BE.service.customer.CustomerService;
import ssginc_kdt_team3.BE.service.owner.OwnerReservationService;

@SpringBootTest
@Transactional
class OwnerReserveServiceTest {

  @Autowired
  OwnerReservationService ownerReserveService;
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