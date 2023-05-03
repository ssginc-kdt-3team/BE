package ssginc_kdt_team3.BE.repository.interfaces.owner;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.domain.Owner;

public interface OwnerJoin extends CrudRepository<Owner,Long> {
    Owner save(Owner owner);
}
