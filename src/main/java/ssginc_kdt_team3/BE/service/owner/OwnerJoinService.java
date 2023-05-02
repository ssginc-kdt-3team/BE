package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.repository.owner.OwnerJoinRepository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class OwnerJoinService {
    private final OwnerJoinRepository ownerJoinRepository;
    private final ModelMapper modelMapper;

    public OwnerJoinDTO join(OwnerJoinDTO ownerJoinDTO){
        Owner owner = modelMapper.map(ownerJoinDTO,Owner.class);
        Owner savedOwner = ownerJoinRepository.save(owner);
        return ModelMapper.map(savedOwner,OwnerJoinDTO.class);
    }
}
