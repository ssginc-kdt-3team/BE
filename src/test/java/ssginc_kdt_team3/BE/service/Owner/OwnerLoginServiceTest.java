//package ssginc_kdt_team3.BE.service.Owner;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import ssginc_kdt_team3.BE.DTOs.Address;
//
//import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
//import ssginc_kdt_team3.BE.DTOs.owner.OwnerLoginDTO;
//import ssginc_kdt_team3.BE.repository.owner.DataOwnerRepository;
//import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
//import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;
//import ssginc_kdt_team3.BE.service.owner.OwnerLoginService;
//import java.time.LocalDate;
//
//@SpringBootTest
//public class OwnerLoginServiceTest {
//
//    @Autowired
//    OwnerLoginService LoginService;
//
//    @Autowired
//    OwnerJoinService JoinService;
//
//    @Autowired
//    JpaDataOwnerRepository repo;
//
//    private OwnerLoginDTO LoginDTO;
//
//    private OwnerJoinDTO owner;
//
//
//
//    @BeforeEach
//    public void 회원세팅 () throws Exception{
//        repo.deleteAll();
//
//        Address address = new Address("???","???구","????3층","123-123");
//        owner = new OwnerJoinDTO("Test4@gmail.com","ㅎㅎㅎㅎㅎ","Zxc@987!!",
//                "010010010",LocalDate.parse("1999-05-05"),
//                true,address);
//        JoinService.join(owner);
//        System.out.println("완료");
//    }
////    @Test
////    public void OwnerLoginTest() throws Exception{
////        LoginDTO = new OwnerLoginDTO("kakao@gmail.com","Zxc@987!!");
////        //비밀번호는 일치하지만 Email은 일치하지 않는경우
////        try{
////            String email = LoginService.loginCheck(LoginDTO);
////            System.out.println(email + "님, 안녕하세요!");
////        }catch (Exception e){
////            System.out.println(e.getMessage());
////        }
////        LoginDTO.setEmail("Test4@naver.com");
////        LoginDTO.setPassword("Zx?????");
////        //Email은 일치하지만 비밀번호는 일치하지 않는경우
////        try{
////            String email = LoginService.loginCheck(LoginDTO);
////            System.out.println(email + "님, 안녕하세요!");
////        }catch (Exception e){
////            System.out.println(e.getMessage());
////        }
////        LoginDTO.setEmail("Test4@gmail.com");
////        LoginDTO.setPassword("Zxc@987!!");
////        //둘다 일치하는 경우
////        try{
////            String email = LoginService.loginCheck(LoginDTO);
////            System.out.println(email + "님, 안녕하세요!");
////        }catch (Exception e){
////            System.out.println(e.getMessage());
////        }
////    }
//
//}
