//package ssginc_kdt_team3.BE.service.Owner;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import ssginc_kdt_team3.BE.DTOs.owner.CheckPwDTO;
//import ssginc_kdt_team3.BE.DTOs.owner.OwnerChangePwDTO;
//import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
//import ssginc_kdt_team3.BE.domain.Owner;
//import ssginc_kdt_team3.BE.enums.UserStatus;
//import ssginc_kdt_team3.BE.repository.owner.DataOwnerRepository;
//import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
//import ssginc_kdt_team3.BE.service.owner.OwnerChangePwService;
//import ssginc_kdt_team3.BE.DTOs.Address;
//import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;
//
//import java.time.LocalDate;
//
//@SpringBootTest
//public class OwnerChangePwTest {
//    @Autowired
//    private JpaDataOwnerRepository repo;
//    @Autowired
//    private OwnerChangePwService ser;
//    @Autowired
//    private OwnerJoinService JoinService;
//    @Autowired
//    private DataOwnerRepository repo2;
//
//    OwnerChangePwDTO pwDTO;
//
//    CheckPwDTO check;
//
//    OwnerJoinDTO ownerJoin;
//
//    @BeforeEach
//    public void 회원세팅 () throws Exception{
//        repo.deleteAll();
//
//        Address address = new Address("서울","어딘가","어떤 건물의 5층","123-123");
//        this.ownerJoin = new OwnerJoinDTO("zxcvbnm@gmail.com","go","Zxc@987!!",
//                "01012349998", LocalDate.parse("1999-11-28"),
//                true,address);
//        JoinService.join(ownerJoin);
//        System.out.println("완료");
//    }
//    @Test
//    public void test1() throws Exception{
//
//        check = new CheckPwDTO("vvvvvvvv@naver.com","go","Zxc@987!!");
//
//        try{
//            ser.CheckPw(check);
//            System.out.println("인증완료");
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        //↑이메일만 다른 경우
//        check.setEmail("zxcvbnm@gmail.com");
//        check.setPassword("!!!");
//
//        try{
//            ser.CheckPw(check);
//            System.out.println("인증완료");
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        //↑비밀번호만 다른 경우
//        check.setName("shin0");
//        check.setPassword("Zxc@987!!");
//
//        try{
//            ser.CheckPw(check);
//            System.out.println("인증완료");
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        //이름만 다른 경우
//        check.setName("go");
//
//        try{
//            ser.CheckPw(check);
//            System.out.println("인증완료");
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        //인증완료
//
//    }
//    @Test
//    public void 비밀번호체크(){
//
//        pwDTO = new OwnerChangePwDTO("qwertyqwerty","11111111111111");
//        System.out.println(pwDTO.getNewPassword1());
//        System.out.println(pwDTO.getNewPassword2());
//
//        try{
//            ser.ChangePw(pwDTO);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        pwDTO.setNewPassword1("11111111111111");
//
//        System.out.println(pwDTO.getNewPassword1());
//        System.out.println(pwDTO.getNewPassword2());
//
//        try{
//            ser.ChangePw(pwDTO);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        String AfterPw = repo2.PasswordMatchEmail("zxcvbnm@gmail.com");
//        System.out.println("변경후 비밀번호 : " + AfterPw);
//    }
//
//}
