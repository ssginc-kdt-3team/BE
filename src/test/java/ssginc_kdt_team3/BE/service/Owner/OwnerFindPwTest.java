//package ssginc_kdt_team3.BE.service.Owner;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import ssginc_kdt_team3.BE.DTOs.Address;
//import ssginc_kdt_team3.BE.DTOs.owner.OwnerFindPwDTO;
//import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
//import ssginc_kdt_team3.BE.DTOs.owner.OwnerNewPwDTO;
//import ssginc_kdt_team3.BE.domain.Owner;
//import ssginc_kdt_team3.BE.enums.UserStatus;
//import ssginc_kdt_team3.BE.repository.owner.DataOwnerRepository;
//import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
//import ssginc_kdt_team3.BE.service.owner.OwnerFindPwService;
//import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;
//
//import java.time.LocalDate;
//
//@SpringBootTest
//public class OwnerFindPwTest {
//
//    @Autowired
//    OwnerFindPwService FindPwService;
//    @Autowired
//    OwnerJoinService JoinService;
//    @Autowired
//    JpaDataOwnerRepository repo;
//    @Autowired
//    DataOwnerRepository repo2;
//
//    OwnerJoinDTO ownerJoin;
//
//    private OwnerFindPwDTO FindPwDTO;
//
//    private OwnerNewPwDTO newPwDTO;
//
//    @BeforeEach
//    public void 회원세팅 () throws Exception{
//        repo.deleteAll();
//
//        Address address = new Address("???","???구","????3층","123-123");
//        this.ownerJoin = new OwnerJoinDTO("Test4@gmail.com","가나다","Zxc@987!!",
//                "01012349998", LocalDate.parse("1999-05-05"),
//                true,address);
//        JoinService.join(ownerJoin);
//        System.out.println("완료");
//    }
//
//        @Test
//        public void before() throws Exception{
//
//            FindPwDTO = new OwnerFindPwDTO("Test4@gmail.com","가나다","01012349998");
//
//            try {
//                FindPwService.findPw(FindPwDTO);
//                System.out.println("성공");
//            }catch (Exception e){
//                System.out.println(e.getMessage());
//            }
//
//            newPwDTO = new OwnerNewPwDTO("qwerty1234","qwerty9999");
//            System.out.println("변경전 비밀번호" + repo2.PasswordMatchEmail(this.ownerJoin.getEmail()));
//            System.out.println("비밀번호를 변경하려는 계정의 Email : " + this.ownerJoin.getEmail());
//
//            try {
//                FindPwService.NewPw(newPwDTO);
//            }catch (Exception e){
//                System.out.println(e.getMessage());
//            }
//            newPwDTO.setNewPassword1("qwerty9999");
//            newPwDTO.setNewPassword2("qwerty9999");
//
//            System.out.println("업데이트 전 비밀번호 : " + repo2.PasswordMatchEmail(this.ownerJoin.getEmail()));
//
//            try {
//                FindPwService.NewPw(newPwDTO);
//                System.out.println("업데이트 된 비밀번호 : " + repo2.PasswordMatchEmail(this.ownerJoin.getEmail()));
//            }catch (Exception e){
//                System.out.println(e.getMessage());
//            }
//        }
//
//}
//
//
