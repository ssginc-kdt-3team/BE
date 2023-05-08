//package ssginc_kdt_team3.BE.service.Owner;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import ssginc_kdt_team3.BE.DTOs.customer.Address;
//import ssginc_kdt_team3.BE.DTOs.owner.OwnerFindPwDTO;
//import ssginc_kdt_team3.BE.DTOs.owner.OwnerNewPwDTO;
//import ssginc_kdt_team3.BE.domain.Owner;
//import ssginc_kdt_team3.BE.enums.UserStatus;
//import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
//import ssginc_kdt_team3.BE.service.owner.OwnerFindPwService;
//import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;
//import ssginc_kdt_team3.BE.service.owner.OwnerNewPwService;
//
//@SpringBootTest
//public class OwnerFindPwTest {
//
//    @Autowired
//    OwnerNewPwService NewpwService;
//    @Autowired
//    OwnerFindPwService ownerFindPwService;
//    @Autowired
//    OwnerJoinService ownerJoinService;
//    @Autowired
//    JpaDataOwnerRepository repo;
//    @Autowired
//    OwnerNewPwService newPwService;
//
//    private OwnerFindPwDTO FindPwDTO;
//
//    private Owner owner;
//
//    private OwnerNewPwDTO newPwDTO;
//
//    @AfterEach
//    public void clean() {
//
//        repo.deleteAll();
//
//    }
//
//        @Test
//        public void before() throws Exception{
//
//            owner = new Owner();
//
//            Address address = new Address("부산시","해운대구","센텀 리더스마크","123-123");
//
//            owner.setEmail("?????@gmail.com");
//            owner.setName("가나다");
//            owner.setPassword("123456789");
//            owner.setPhoneNumber("010-1234-9998");
//            owner.setAddress(address);
//            owner.setGender(false);
//            owner.setStatus(UserStatus.ACTIVE);
//
//            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//            repo.save(owner);
//            System.out.println("??????????????????????????????");
//            FindPwDTO = new OwnerFindPwDTO("?????@gmail.com","가나다","010-1234-9998");
//
//            try {
//                ownerFindPwService.findPw(FindPwDTO);
//                System.out.println("성공");
//            }catch (Exception e){
//                System.out.println(e.getMessage());
//            }
//
//            newPwDTO = new OwnerNewPwDTO("qwerty1234","qwerty9999");
//            try {
//                NewpwService.NewPw(newPwDTO);
//                System.out.println("업데이트 된 비밀번호 : " + repo.PasswordMatchEmail(owner.getEmail()));
//            }catch (Exception e){
//                System.out.println(e.getMessage());
//            }
//            newPwDTO.setNewPassword1("qwerty9999");
//            newPwDTO.setNewPassword2("qwerty9999");
//
//            try {
//                NewpwService.NewPw(newPwDTO);
//                System.out.println("업데이트 된 비밀번호 : " + repo.PasswordMatchEmail(owner.getEmail()));
//            }catch (Exception e){
//                System.out.println(e.getMessage());
//            }
//        }
//
//}
//
//
