package ssginc_kdt_team3.BE.repository.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssginc_kdt_team3.BE.DTOs.branch.BranchShopDTO;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerListDTO;

import ssginc_kdt_team3.BE.DTOs.customer.CustomerPersonalizeShopDTO;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Grade;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JpaDataCustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select new ssginc_kdt_team3.BE.DTOs.customer.CustomerListDTO(c.id, c.name, c.email, c.status, c.grade.name) from Customer c")
    Page<CustomerListDTO> findAllBy(Pageable pageable);

//    Page<Customer> findAllBy(Pageable pageable);
//    @Query("select c from Customer c inner join User u on c.id = u.id where u.email = :Email")
    Optional<Customer> findCustomerByEmail(String email);

    @Query("SELECT new ssginc_kdt_team3.BE.DTOs.branch.BranchShopDTO(s.id, s.name, s.location, s.shopImgUrl,s.status, s.category) " +
            "FROM Branch b " +
            "LEFT JOIN Shop s ON b.id = s.branch.id " +
            "WHERE b.id = :id")
    List<BranchShopDTO> BranchIdByShop(@Param("id")Long id);

//    @Query("SELECT new ssginc_kdt_team3.BE.DTOs.branch.BranchShopDTO(s.id, s.name, s.location, s.shopImgUrl,s.status, s.category) " +
//            "FROM Branch b " +
//            "LEFT JOIN Shop s ON b.id = s.branch.id " +
//            "WHERE s.id = :id")
//    BranchShopDTO ShopIdByShop(@Param("id")Long id);

    @Query("SELECT new ssginc_kdt_team3.BE.DTOs.customer.CustomerPersonalizeShopDTO(s.id, s.name, s.shopImgUrl, s.info, b.id) " +
            "FROM Shop s " +
            "LEFT JOIN Branch b ON s.branch.id = b.id " +
            "WHERE s.id = :id")
    CustomerPersonalizeShopDTO ShopIdByPersonalizeShop(@Param("id")Long id);

    // 0526 이현: 고객 개인정보찾기 기능위해 추가
    Optional<Customer> findByNameAndPhoneNumber(String name, String phone);
    Optional<Customer> findByNameAndEmailAndPhoneNumber(String name, String email, String phone);
    List<Customer> findAllByGradeChangeDate(LocalDate start);

    @Query("SELECT g FROM Grade g WHERE g.id = :id")
    Grade gradeFindById(@Param("id") Long id);

    Optional<Customer> findByEmail(String email);

}
