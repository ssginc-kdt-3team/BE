package ssginc_kdt_team3.BE.service.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.personalizeruntime.model.PredictedItem;
import ssginc_kdt_team3.BE.DTOs.branch.BranchShopDTO;
import java.util.*;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.personalizeruntime.PersonalizeRuntimeClient;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsRequest;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsResponse;
import ssginc_kdt_team3.BE.repository.customer.JpaDataCustomerRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerPersonalizeShopService {

    private final JpaDataCustomerRepository repository;

    public List<BranchShopDTO> customerPersonalizeShop(Long BranchId, Long UserId){

        // Personalize 서비스 클라이언트 생성
        PersonalizeRuntimeClient personalizeClient = PersonalizeRuntimeClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        // GetRecommendations 요청 생성
        Map<String,String> filterValues = new HashMap<>();
        filterValues.put("branch_id",String.valueOf(BranchId));

        String userId = String.valueOf(UserId);

        GetRecommendationsRequest request = GetRecommendationsRequest.builder()
                .campaignArn("arn:aws:personalize:ap-northeast-2:556683426101:campaign/no-yess-team-campaign")
                .filterArn("arn:aws:personalize:ap-northeast-2:556683426101:filter/branch_id-equal-item_id")
                .filterValues(filterValues)
                .numResults(3)
//                .recommenderArn("arn:aws:personalize:ap-northeast-2:556683426101:recommender/no-yess-team-recommenders")
                .userId(userId)
                .build();

        // GetRecommendations 요청 보내기
        GetRecommendationsResponse response = personalizeClient.getRecommendations(request);



        List<PredictedItem> items = response.itemList();

        List<BranchShopDTO> branchShopDTOS = new ArrayList<>();

        for (PredictedItem item: items) {
            Long shopId = Long.valueOf(item.itemId());

            log.info("UserId = {}",UserId);
            log.info("BranchId = {}",BranchId);
            log.info("Response ShopId = {}",shopId);

            //스키마에는 String 타입으로 정의되어 있어서 Long 타입으로 다시 변환
            //itemId = ShopId
            BranchShopDTO dto = repository.ShopIdByShop(shopId);
            if (dto != null){

                branchShopDTOS.add(dto);

                log.info("ShopId = {}",dto.getId());
                log.info("ShopName = {}",dto.getName());
                log.info("ShopStatus = {}",dto.getShopStatus());
            }
        }
        return branchShopDTOS;
    }


}
