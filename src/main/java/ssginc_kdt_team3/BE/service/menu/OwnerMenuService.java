package ssginc_kdt_team3.BE.service.menu;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ssginc_kdt_team3.BE.DTOs.branch.BranchDetailDTO;
import ssginc_kdt_team3.BE.DTOs.menu.MenuAddDTO;
import ssginc_kdt_team3.BE.domain.Branch;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.domain.ShopMenu;
import ssginc_kdt_team3.BE.repository.menu.JpaDataShopMenuRepository;
import ssginc_kdt_team3.BE.repository.shop.JpaDataShopRepository;
import ssginc_kdt_team3.BE.util.FileUploadUtil;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OwnerMenuService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${menuLocation}")
    String menuDir;

    private final JpaDataShopMenuRepository menuRepository;
    private final JpaDataShopRepository shopRepository;
    private final AmazonS3Client amazonS3Client;

    public boolean createNewMenu(MenuAddDTO addDTO, MultipartFile multipartFile) {

        Optional<Shop> shopByOwnerId = shopRepository.findShopByOwner_id(addDTO.getOwnerId());

        if (shopByOwnerId.isPresent()) {
            Shop shop = shopByOwnerId.get();
            try {
                String imgUrl = uploadS3(menuDir, multipartFile);

                ShopMenu menu = new ShopMenu(addDTO,imgUrl, shop);

                menuRepository.save(menu);

                return true;

            } catch (IOException e) {
                return false;
            }
        }
        return false;
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
