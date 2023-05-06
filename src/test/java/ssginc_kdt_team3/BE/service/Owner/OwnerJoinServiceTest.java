package ssginc_kdt_team3.BE.service.Owner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
class OwnerJoinServiceTest {

    @Autowired
    JpaDataOwnerRepository repo;
    @Autowired
    OwnerJoinService ser;

    private OwnerJoinDTO ownerJoinDTO;

    private OwnerJoinDTO owner2JoinDTO;

    @BeforeEach
    public void SetOwner(){

        ownerJoinDTO = new OwnerJoinDTO();
    }
    @Test
    public void joinServiceTest1() throws Exception{
        ownerJoinDTO.setName("User1");
        ownerJoinDTO.setEmail("User1@naver.com");
        ownerJoinDTO.setPassword("Qwe@123!!");
        ownerJoinDTO.setPhone("010-1234-5678");
        ownerJoinDTO.setGender(false);
        ownerJoinDTO.setBirthday(LocalDate.parse("1997-01-25"));

        try{
            ser.join(ownerJoinDTO);
        }catch (Exception e){
            throw new Exception();
        }
    }

    @BeforeEach
    public void SetOwner2(){
        owner2JoinDTO = new OwnerJoinDTO();
    }

    @Test
    public void joinServiceTest2() throws Exception {
        owner2JoinDTO.setName("User22");
        owner2JoinDTO.setEmail("User22@naver.com");
        owner2JoinDTO.setPassword("Zxc@987!!");
        owner2JoinDTO.setPhone("010-9999-8888");
        owner2JoinDTO.setGender(true);
        owner2JoinDTO.setBirthday(LocalDate.parse("1999-05-05"));

        try {
            ser.join(owner2JoinDTO);
        } catch (Exception e) {
            throw new Exception();
        }

        Optional<Owner> owner2 = repo.findByEmail(owner2JoinDTO.getEmail());
        System.out.println("JoinTest결과 : " + owner2);

    }
    @AfterEach
    public void clean() {

        repo.deleteAll();
    }

}











