package ssginc_kdt_team3.BE.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssginc_kdt_team3.BE.DTOs.customer.Address;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerListDTO;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerUpdateDTO;

import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ssginc_kdt_team3.BE.DTOs.customer.*;
import ssginc_kdt_team3.BE.enums.UserStatus;
import ssginc_kdt_team3.BE.repository.customer.JpaDataCustomerRepository;


import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminCustomerService {

    private final JpaDataCustomerRepository customerRepository;
//    private final EntityManager em;
//
//    JPQLQueryFactory queryFactory;

    public Page<CustomerListDTO> findAllCustomer(Pageable pageable) {
        return customerRepository.findAllBy(pageable);
    }

    public Optional<Customer> temp(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public CustomerDetailDTO findCustomerById(Long customerId) {

        Optional<Customer> byId = customerRepository.findById(customerId);

        if (byId.isPresent()) {
            Customer customer = byId.get();
            CustomerDetailDTO customerDetailDTO = new CustomerDetailDTO(customer.getId(), customer.getEmail(), customer.getPassword(),
                customer.getName(), customer.getPhoneNumber(), customer.getGender(), customer.getBirthday(), customer.getAddress(),
                customer.getRole(), customer.getStatus(), customer.getGrade().toString());
            // 0506 이현: Grade name부분 enum 추가로 기존 Customer.getGrade().getName() 에러 -> Customer.getGrade().toString() 으로 수정


            return customerDetailDTO;
        }
        return new CustomerDetailDTO();
    }


    public CustomerDetailDTO findCustomerByEmail(String customerEmail) {
        Optional<Customer> byEmail = customerRepository.findCustomerByEmail(customerEmail);

        if (byEmail.isPresent()) {
            Customer customer = byEmail.get();
            CustomerDetailDTO customerDetailDTO = new CustomerDetailDTO(customer.getId(), customer.getEmail(), customer.getPassword(),
                customer.getName(), customer.getPhoneNumber(), customer.getGender(), customer.getBirthday(), customer.getAddress(),
                customer.getRole(), customer.getStatus(), customer.getGrade().toString());


            return customerDetailDTO;
        }

        return null;
    }

    public boolean updateCustomerInfo(Long customerId, CustomerUpdateDTO customerDTO) {

        Optional<Customer> customer = customerRepository.findById(customerId);


        String name = customerDTO.getName();
        String password = customerDTO.getPassword();
        String phone = customerDTO.getPhone();
        Address address = customerDTO.getAddress();
        UserStatus status = customerDTO.getStatus();
        Grade grade = customerDTO.getGrade();


        if (customer.isPresent()) {
            Customer findCustomer = customer.get();

            findCustomer.setPassword(password);
            findCustomer.setName(name);
            findCustomer.setPhoneNumber(phone);
            findCustomer.setAddress(address);
            findCustomer.setStatus(status);
            findCustomer.setGrade(grade);

            return true;
        } else {
            return false;
        }
    }

}