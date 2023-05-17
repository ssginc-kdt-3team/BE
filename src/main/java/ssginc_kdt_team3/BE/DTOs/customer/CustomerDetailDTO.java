package ssginc_kdt_team3.BE.DTOs.customer;

import lombok.Data;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.enums.UserRole;
import ssginc_kdt_team3.BE.enums.UserStatus;

import java.time.LocalDate;

@Data
public class CustomerDetailDTO {

    private Long id;

    private String email;

    private String password;

    private String name;

    private String phone;

    private Boolean gender;

    private LocalDate birthday;

//    private String city;
//    private String district;
//    private String detail;
//    private String zipCode;

    private Address address;

    private UserRole role;

    private UserStatus status;

    private String grade = null;

    public CustomerDetailDTO(Customer customer) {
        this.id = customer.getId();
        this.email = customer.getEmail();
        this.password = customer.getPassword();
        this.name = customer.getName();
        this.phone = customer.getPhoneNumber();
        this.gender = customer.getGender();
        this.birthday = customer.getBirthday();
        this.address = customer.getAddress();
        this.role = customer.getRole();
        this.status = customer.getStatus();
        this.grade = customer.getGrade().getName().toString();
    }

    public CustomerDetailDTO() {
    }
}
