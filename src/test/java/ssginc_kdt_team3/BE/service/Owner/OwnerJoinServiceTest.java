//package ssginc_kdt_team3.BE.service.Owner;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
//import ssginc_kdt_team3.BE.domain.Owner;
//import ssginc_kdt_team3.BE.repository.owner.DataOwnerRepository;
//import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
//import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;
//import ssginc_kdt_team3.BE.DTOs.customer.Address;
//import java.time.LocalDate;
//import java.util.Optional;
//
//@SpringBootTest
//class OwnerJoinServiceTest {
//
//    @Autowired
//    JpaDataOwnerRepository repo;
//
//    @Autowired
//    OwnerJoinService service;
//
//    @BeforeEach
//    public void clean(){
//        repo.deleteAll();
//    }
//
//    @Test
//    public void joinServiceTest2() throws Exception{
//
//        OwnerJoinDTO owners;
//
//        Address address = new Address("부산시","해운대구","센텀 리더스마크","123-123");
//        owners = new OwnerJoinDTO("User223334@naver.com","User22","Zxc@987!!","01099998888",LocalDate.parse("1999-05-05"),true,address);
//        System.out.println(owners.toString());
//        service.join(owners);
//
//        String email = owners.getEmail();
//        Optional<Owner> byEmail = repo.findByEmail(email);
//        String email1 = byEmail.get().getEmail();
//        System.out.println("회원가입 결과 : " + email1);
//
//        Address address2 = new Address("서울시","서초구","33층","123-123");
//        owners = new OwnerJoinDTO("User24542@naver.com","가나다","Zxc@987!!","010-3333-3333",LocalDate.parse("1999-05-05"),true,address2);
//
//        try {
//            service.join(owners);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
