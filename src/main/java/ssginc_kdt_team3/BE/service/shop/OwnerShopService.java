package ssginc_kdt_team3.BE.service.shop;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ssginc_kdt_team3.BE.DTOs.menu.MenuDTO;
import ssginc_kdt_team3.BE.DTOs.shop.OwnerShopDetailDTO;
import ssginc_kdt_team3.BE.DTOs.shop.OwnerShopUpdateDTO;
import ssginc_kdt_team3.BE.DTOs.shop.ShopAddDTO;
import ssginc_kdt_team3.BE.domain.*;
import ssginc_kdt_team3.BE.enums.ShopStatus;
import ssginc_kdt_team3.BE.repository.branch.JpaDataBranchRepository;
import ssginc_kdt_team3.BE.repository.menu.JpaDataShopMenuRepository;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;
import ssginc_kdt_team3.BE.repository.shop.JpaDataShopOperationInfoRepository;
import ssginc_kdt_team3.BE.repository.shop.JpaDataShopRepository;
import ssginc_kdt_team3.BE.util.FileUploadUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OwnerShopService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${shopLocation}")
    String shopDir;

    @Value("${businessLocation}")
    String businessLocation;

    private final JpaDataShopRepository shopRepository;
    private final JpaDataShopOperationInfoRepository shopOperationInfoRepository;
    private final JpaDataOwnerRepository ownerRepository;
    private final JpaDataBranchRepository branchRepository;
    private final AmazonS3Client amazonS3Client;
    private final JpaDataShopMenuRepository shopMenuRepository;

    //신규 매장 등록
    public Optional<Long> addNewShop(ShopAddDTO addDTO, MultipartFile businessImg, MultipartFile shopImg) throws IOException {

        String businessImgUrl = uploadS3(businessLocation, businessImg);
        String shopImgUrl = uploadS3(shopDir, shopImg);

        Optional<Branch> findBranch = branchRepository.findById(addDTO.getBranchId());
        Optional<Owner> findOwner = ownerRepository.findById(addDTO.getOwnerId());

        if (findBranch.isPresent() && findOwner.isPresent()) {

            Owner owner = findOwner.get();
            Branch branch = findBranch.get();

            ShopOperationInfo shopOperationInfo = ShopOperationInfo.builder()
                    .openTime(addDTO.getOpenTime())
                    .closeTime(addDTO.getCloseTime())
                    .orderCloseTime(addDTO.getOrderCloseTime())
                    .openDate(addDTO.getOpenDay())
                    .seats(addDTO.getSeat()).build();

            ShopOperationInfo saveShopOperationInfo = shopOperationInfoRepository.save(shopOperationInfo);

            Shop shop = Shop.builder()
                    .businessNum(addDTO.getBusinessNumber())
                    .businessName(addDTO.getBusinessCeo())
                    .businessImg(businessImgUrl)
                    .shopImg(shopImgUrl)
                    .name(addDTO.getShopName())
                    .location(addDTO.getLocation())
                    .info(addDTO.getShopInfo())
                    .branch(branch)
                    .owner(owner)
                    .status(ShopStatus.OPEN)
                    .operationInfo(saveShopOperationInfo).build();

            Shop save = shopRepository.save(shop);

            return Optional.ofNullable(save.getId());
        }
        return Optional.ofNullable(null);
    }

    public Optional<OwnerShopDetailDTO> showShopDetail(Long ownerId) {

        Optional<Shop> shopByOwnerId = shopRepository.findShopByOwner_id(ownerId);

        if (shopByOwnerId.isPresent()) {
            Shop shop = shopByOwnerId.get();
            List<MenuDTO> menus = new ArrayList<>();

            List<ShopMenu> allByShopId = shopMenuRepository.findAllByShop_Id(shop.getId());
            for (ShopMenu shopMenu : allByShopId) {
                MenuDTO menuDTO = new MenuDTO(shopMenu);
                menus.add(menuDTO);
            }

            OwnerShopDetailDTO ownerShopDetailDTO = new OwnerShopDetailDTO(shop, menus);
            return Optional.ofNullable(ownerShopDetailDTO);
        }
        return Optional.ofNullable(null);
    }

    public boolean updateShop(Long shopId, OwnerShopUpdateDTO updateDTO, MultipartFile multipartFile){
        Optional<Shop> byId = shopRepository.findById(shopId);

        if (byId.isPresent()) {
            Shop shop = byId.get();

            try {
                if (!multipartFile.isEmpty()) {
                    updateDTO.setShopImgUrl(uploadS3(shopDir, multipartFile));
                }
            } catch (IOException e) {
                return false;
            }

            return shop.update(shopId, updateDTO);
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
