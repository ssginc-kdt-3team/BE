package ssginc_kdt_team3.BE.service.Owner;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerFindPwDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerNewPwDTO;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
import ssginc_kdt_team3.BE.service.owner.OwnerFindPwService;
import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;
import ssginc_kdt_team3.BE.service.owner.OwnerNewPwService;

import java.time.LocalDate;

@SpringBootTest
public class OwnerFindPwTest {

    @Autowired
    OwnerNewPwService NewpwService;
    @Autowired
    OwnerFindPwService ownerFindPwService;
    @Autowired
    OwnerJoinService ownerJoinService;
    @Autowired
    JpaDataOwnerRepository repo;
    @Autowired
    OwnerNewPwService newPwService;

    private OwnerFindPwDTO FindPwDTO;

    private OwnerJoinDTO joinDTO;

    private OwnerNewPwDTO newPwDTO;

    @AfterEach
    public void clean() {

        repo.deleteAll();
    }

        @Test
        public void before() throws  Exception{
            FindPwDTO = new OwnerFindPwDTO();
            joinDTO = new OwnerJoinDTO();
            newPwDTO = new OwnerNewPwDTO();

            joinDTO.setName("User123");
            joinDTO.setEmail("User123@naver.com");
            joinDTO.setPassword("Qwe@123123!!");
            joinDTO.setPhone("010-1234-9999");
            joinDTO.setGender(false);
            joinDTO.setBirthday(LocalDate.parse("1997-01-25"));

            ownerJoinService.join(joinDTO);

            FindPwDTO.setName("User123");
            FindPwDTO.setEmail("User123@naver.com");
            FindPwDTO.setPhone("010-1234-9998");
            try {
                ownerFindPwService.findPw(FindPwDTO);
                System.out.println("=============FindPwService=============");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            newPwDTO.setNewPassword1("1234568889");
            newPwDTO.setNewPassword2("123456789");

            try {
                NewpwService.NewPw(newPwDTO);
                System.out.println("=============NewPw=============");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }



    }

//    @Test
//    @Transactional
//    public void TemporaryPw(){
//        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+";
//        StringBuilder TempPw = new StringBuilder();
//        Random random = new Random();
//        int length = 10;
//        for(int i = 0; i < length;i++){
//            int index = random.nextInt(alphabet.length());
//            TempPw.append(alphabet.charAt(index));
//        }
//
//        String TempPassword = TempPw.toString();
//        repo.updatePassword(TempPassword);
//
//        System.out.println("666666666666666666");
//        System.out.println(TempPassword);
//    }

