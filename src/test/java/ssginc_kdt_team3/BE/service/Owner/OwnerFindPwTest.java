package ssginc_kdt_team3.BE.service.Owner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerFindPwDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
import ssginc_kdt_team3.BE.service.owner.OwnerFindPwService;
import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
public class OwnerFindPwTest {

    @Autowired
    OwnerFindPwService ownerFindPwService;
    @Autowired
    OwnerJoinService ownerJoinService;
    @Autowired
    JpaDataOwnerRepository repo;

    private OwnerFindPwDTO ownerFindPwDTO;

    private OwnerJoinDTO owner3JoinDTO;

    private Owner owner;

    @BeforeEach
    public void before() {
            ownerFindPwDTO = new OwnerFindPwDTO();
            owner3JoinDTO = new OwnerJoinDTO();
            owner = new Owner();
    }
    @BeforeEach
    public void clean() {

        repo.deleteAll();
    }
    @Test
    public void findTest()throws Exception{

        owner3JoinDTO.setName("User123");
        owner3JoinDTO.setEmail("User123@naver.com");
        owner3JoinDTO.setPassword("Qwe@123123!!");
        owner3JoinDTO.setPhone("010-1234-9999");
        owner3JoinDTO.setGender(false);
        owner3JoinDTO.setBirthday(LocalDate.parse("1997-01-25"));

        try {
            ownerJoinService.join(owner3JoinDTO);
            System.out.println("OwnerJoinTest! : " + owner3JoinDTO);
            System.out.println("==================================");
        }
        catch (Exception e){
            throw new Exception();
        }

        ownerFindPwDTO.setName("User123");
        ownerFindPwDTO.setEmail("User123@naver.com");
        ownerFindPwDTO.setPhone("010-1234-9999");

        System.out.println("FindPwDTO Setting Test : " + ownerFindPwDTO);
        System.out.println("==================================");

        boolean emailCheck = repo.existsByEmail(ownerFindPwDTO.getEmail());
        System.out.println("existsByEmail? " + emailCheck);
        System.out.println("==================================");


        Optional<Owner> InfoOwner = repo.findByEmail(ownerFindPwDTO.getEmail());
        System.out.println("Exists Account " + InfoOwner);
        System.out.println("==================================");

        Owner EmaillMat = InfoOwner.get();

        String RepoEmail = EmaillMat.getEmail();
        String RepoName = EmaillMat.getName();
        String RepoPhone = EmaillMat.getPhone();

        System.out.println(RepoEmail);
        System.out.println(RepoName);
        System.out.println(RepoPhone);
        System.out.println("=============================");
        try {
            ownerFindPwService.findPw(ownerFindPwDTO);
            String pw = ownerFindPwService.TemporaryPw();
            System.out.println("TemporaryPw =" + pw);
        }catch (Exception e){
            throw new Exception();
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
}
