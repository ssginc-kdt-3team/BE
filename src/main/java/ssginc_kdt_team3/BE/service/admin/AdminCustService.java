package ssginc_kdt_team3.BE.service.admin;

import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.customer.Address;
import ssginc_kdt_team3.BE.DTOs.customer.CustomeromerListDTO;
import ssginc_kdt_team3.BE.DTOs.customer.CustomeromerUpdateDTO;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ssginc_kdt_team3.BE.DTOs.Customer.*;
import ssginc_kdt_team3.BE.domain.QCustomer;
import ssginc_kdt_team3.BE.repository.Customer.JpaDateCustomerRepository;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminCustomerService {

    private final JpaDateCustomerRepository CustomerRepository;
//    private final EntityManager em;

    JPQLQueryFactory queryFactory;

    public Page<CustomeromerListDTO> findAllCustomer(Pageable pageable) {
        return CustomerRepository.findAllBy(pageable);
    }

    public Optional<Customer> temp(Long CustomerId) {
        return CustomerRepository.findById(CustomerId);
    }

    public CustomeromerDetailDTO findCustomerById(Long CustomerId) {

        Optional<Customer> byId = CustomerRepository.findById(CustomerId);

        if (byId.isPresent()) {
            Customer Customer = byId.get();
            CustomeromerDetailDTO CustomeromerDetailDTO = new CustomeromerDetailDTO(Customer.getId(), Customer.getEmail(), Customer.getPassword(),
                    Customer.getName(), Customer.getPhone(), Customer.getGender(), Customer.getBirthday(), Customer.getAddress(),
                    Customer.getRole(), Customer.getStatus(), Customer.getGrade().toString());
            // 0506 이현: Grade name부분 enum 추가로 기존 Customer.getGrade().getName() 에러 -> Customer.getGrade().toString() 으로 수정

            return CustomeromerDetailDTO;
        }


        return new CustomeromerDetailDTO();
    }

    public Optional<Customer> findCustomerById2(Long CustomerId) {
        QCustomer Customer = new QCustomer("Customer");

        Optional<Customer> findCustomer= Optional.of(queryFactory
                .select(Customer)
                .from(Customer)
                .where(Customer.id.eq(CustomerId))
                .fetchOne());

        return findCustomer;
    }

    public CustomeromerDetailDTO findCustomerByEmail(String CustomerEmail) {
        Optional<Customer> byEmail = CustomerRepository.findCustomerByEmail(CustomerEmail);

        if (byEmail.isPresent()) {
            Customer Customer = byEmail.get();
            CustomeromerDetailDTO CustomeromerDetailDTO = new CustomeromerDetailDTO(Customer.getId(), Customer.getEmail(), Customer.getPassword(),
                    Customer.getName(), Customer.getPhone(), Customer.getGender(), Customer.getBirthday(), Customer.getAddress(),
                    Customer.getRole(), Customer.getStatus(), Customer.getGrade().toString());
            // 0506 이현: Grade name부분 enum 추가로 기존 코드 수정

            return CustomeromerDetailDTO;
        }

        return null;
    }

    public boolean updateCustomerInfo(Long CustomerId, CustomeromerUpdateDTO CustomerDTO) {

        Optional<Customer> Customer = CustomerRepository.findById(CustomerId);

        String name = CustomerDTO.getName();
        String password = CustomerDTO.getPassword();
        String phone = CustomerDTO.getPhone();
        Address address = CustomerDTO.getAddress();
        UserStatus status = CustomerDTO.getStatus();
        Grade grade = CustomerDTO.getGrade();

        if (Customer.isPresent()) {
            Customer findCustomer = Customer.get();

            findCustomer.setPassword(password);
            findCustomer.setName(name);
            findCustomer.setPhone(phone);
            findCustomer.setAddress(address);
            findCustomer.setStatus(status);
            findCustomer.setGrade(grade);

            return true;
        } else {
            return false;
        }
    }

}