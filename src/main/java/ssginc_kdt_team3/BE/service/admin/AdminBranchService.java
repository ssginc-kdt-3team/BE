package ssginc_kdt_team3.BE.service.admin;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ssginc_kdt_team3.BE.DTOs.branch.BranchAddDTO;
import ssginc_kdt_team3.BE.DTOs.branch.BranchAdminListDTO;
import ssginc_kdt_team3.BE.DTOs.branch.BranchDetailDTO;
import ssginc_kdt_team3.BE.DTOs.branch.BranchUpdateDTO;
import ssginc_kdt_team3.BE.domain.Branch;
import ssginc_kdt_team3.BE.domain.BranchOperationInfo;
import ssginc_kdt_team3.BE.enums.BranchStatus;
import ssginc_kdt_team3.BE.repository.branch.JpaDataBranchInfoRepository;
import ssginc_kdt_team3.BE.repository.branch.JpaDataBranchRepository;
import ssginc_kdt_team3.BE.util.FileUploadUtil;
import ssginc_kdt_team3.BE.util.TimeUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminBranchService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${imglocation}")
    String branchLocation;

    private final JpaDataBranchRepository branchRepository;
    private final JpaDataBranchInfoRepository branchInfoRepository;
    private final AmazonS3Client amazonS3Client;

    public Optional<Long> addNewBranch(BranchAddDTO dto, MultipartFile multipartFile) {

        try {

            String branchImgUrl = uploadS3(branchLocation, multipartFile);

            log.info("============================================================ 업로드 주소 : {}", branchImgUrl);

            LocalTime open = TimeUtils.stringParseLocalTime(dto.getOpenTime());
            LocalTime close = TimeUtils.stringParseLocalTime(dto.getCloseTime());
            LocalDate openDay = TimeUtils.stringParseLocalDate(dto.getOpenDay());

            BranchOperationInfo operationInfo = BranchOperationInfo.builder()
                    .openTime(open)
                    .closeTime(close)
                    .openDay(openDay).build();

            Branch branch = Branch.builder()
                    .name(dto.getName())
                    .address(dto.getAddress())
                    .phone(dto.getPhone())
                    .imgUrl(branchImgUrl)
                    .branchOperationInfo(operationInfo)
                    .status(BranchStatus.OPEN)
                    .build();

            BranchOperationInfo infoSave = branchInfoRepository.save(operationInfo);
            Branch save = branchRepository.save(branch);

            return Optional.ofNullable(save.getId());
        } catch(Exception e) {
            return Optional.empty();
        }
    }

    public Optional<BranchDetailDTO> findOneBranch(Long branchId) {
        Optional<Branch> byId = branchRepository.findById(branchId);

        if (byId.isPresent()) {
            Branch branch = byId.get();

            BranchDetailDTO branchDetailDTO = new BranchDetailDTO(branch);
            System.out.println(branchDetailDTO.getOpenDay());
            return Optional.ofNullable(branchDetailDTO);
        }

        return Optional.ofNullable(null);
    }

    public boolean deleteBranch(Long branchId) {
        Optional<Branch> byId = branchRepository.findById(branchId);

        if (byId.isPresent()) {
            Branch branch = byId.get();

            return branch.delete(branchId);
        }
        return false;
    }

    public boolean updateBranch(Long branchId, BranchUpdateDTO updateDTO, MultipartFile multipartFile){
        Optional<Branch> byId = branchRepository.findById(branchId);

        if (byId.isPresent()) {
            Branch branch = byId.get();

            String branchImgUrl = "";

            try {
                if (!multipartFile.isEmpty()) {
                    branchImgUrl = uploadS3(branchLocation, multipartFile);
                }
            } catch (IOException e) {
                return false;
            }

            return branch.update(branchId, updateDTO, branchImgUrl);
        }
        return false;
    }

    public List<BranchAdminListDTO> showAllBranchForAdmin() {
        List<Branch> all = branchRepository.findAll();
        List<BranchAdminListDTO> result = new ArrayList<>();
        for (Branch branch : all) {
            result.add( new BranchAdminListDTO(branch));
        }

        return result;
    }

    private String uploadS3(String dir, MultipartFile mf) throws IOException {

        String uuid = UUID.randomUUID().toString();

        File file = FileUploadUtil.saveFile(mf)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        String fileName = dir + uuid;
        String uploadImageUrl = putS3(file, fileName);
        removeNewFile(file);

        return uploadImageUrl;
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }
}
