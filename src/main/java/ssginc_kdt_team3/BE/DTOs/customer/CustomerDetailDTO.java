package ssginc_kdt_team3.BE.DTOs.customer;

import lombok.Data;
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

    public CustomerDetailDTO(Long id, String email, String password, String name, String phone, Boolean gender, LocalDate birthday, Address address, UserRole role, UserStatus status, String grade) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.role = role;
        this.status = status;
        this.grade = grade;
    }

    public CustomerDetailDTO() {
    }
}
