package ssginc_kdt_team3.BE.repository.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.domain.Owner;
import javax.persistence.EntityManager;
import java.util.Optional;



@Repository
@RequiredArgsConstructor
@Transactional
public class DataOwnerRepository{

    private final EntityManager em;

    public boolean existsEmail(String email) {
        Long count = em.createQuery("SELECT COUNT(o) FROM Owner o WHERE o.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();


        boolean emailCheck = count > 0;
        //이메일이 존재하면 1
        //1 > 0 = emailCheck = true(1)
        //이메일 존재시 true반환

        //이메일이 존재하지않으면 0
        //1 > 0 = emailCheck = false(0)
        //이메일이 존재하지 않으면 false반환
        return emailCheck;
    }

//    public void save(Owner owner){
//        em.createQuery("INSERT INTO Owner(email, password, name, phone,birthday, gender, address, status, role)" +
//                        "VALUES(:email, :password, :name, :phone, :birthday, :gender, :address, :status, :role)")
//                .setParameter("email", owner.getEmail())
//                .setParameter("password", owner.getPassword())
//                .setParameter("name", owner.getName())
//                .setParameter("phone", owner.getPhoneNumber())
//                .setParameter("birthday", owner.getBirthday())
//                .setParameter("gender", owner.getGender())
//                .setParameter("address", owner.getAddress())
//                .setParameter("status", owner.getStatus())
//                .setParameter("role", owner.getRole())
//                .executeUpdate();
//    }

//    public void delete(String email){
//        em.createQuery("DELETE FROM Owner o WHERE o.email = :email")
//                .setParameter("email", email)
//                .executeUpdate();
//    }

    public Optional<Owner> findByEmail(String email){
        try {
            return Optional.of(em.createQuery("SELECT o FROM Owner o WHERE o.email = :email", Owner.class)
                    .setParameter("email", email)
                    .getSingleResult());
        }catch (NullPointerException e){
            return Optional.empty();
        }
    }

    public String PasswordMatchEmail(String email){
        return em.createQuery("SELECT o.password FROM Owner o WHERE o.email = :email",String.class)
                .setParameter("email",email)
                .getSingleResult();
    }

    public void updatePassword(String email,String password){

        em.createQuery("UPDATE Owner o SET o.password = :password WHERE o.email = :email")
                .setParameter("password",password)
                .setParameter("email",email)
                .executeUpdate();
    }

    public Optional<Owner> findById(long id){
        try {
            return Optional.of(em.createQuery("SELECT o FROM Owner o WHERE o.id = :id",Owner.class)
                    .setParameter("id",id)
                    .getSingleResult());
        }catch (NullPointerException e){
            return Optional.empty();
        }
    }

    public void updateOwnerInfo(Owner owner,long id){
//    em.getTransaction().begin();
        em.createQuery("UPDATE Owner o SET o.phone = :phone, o.address = :address , o.status = :status " +
                        "WHERE o.id = :id")
                .setParameter("phone",owner.getPhoneNumber())
                .setParameter("address",owner.getAddress())
                .setParameter("status",owner.getStatus())
                .setParameter("id",owner.getId());
//    em.getTransaction().commit();
    }

}
