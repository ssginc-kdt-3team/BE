package ssginc_kdt_team3.BE.repository.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.interfaces.owner.OwnerJoin;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OwnerJoinRepository implements OwnerJoin {
    private final EntityManager em;


    @Override
    public Owner save(Owner owner) {
        em.merge(owner);
        return owner;
    }

    @Override
    public <S extends Owner> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Owner> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Owner> findAll() {
        return null;
    }

    @Override
    public Iterable<Owner> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Owner entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Owner> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
