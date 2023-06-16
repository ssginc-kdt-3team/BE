package ssginc_kdt_team3.BE.service.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.personalizeruntime.model.PredictedItem;
import java.util.*;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.personalizeruntime.PersonalizeRuntimeClient;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsRequest;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsResponse;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerPersonalizeShopDTO;
import ssginc_kdt_team3.BE.repository.customer.JpaDataCustomerRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerPersonalizeShopService {

    private final JpaDataCustomerRepository repository;

    public List<CustomerPersonalizeShopDTO> customerPersonalizeShop(Long UserId){

        List<CustomerPersonalizeShopDTO> personalizeShopDTOS = new ArrayList<>();

        if(UserId == -1) {//로그인한 상태가 아닐경우 랜덤으로 매장8개 추천

            Random random = new Random();

            long[] randomNumbers = new long[8];

            Set<Long> setNumbers = new HashSet<>();

            for (int i = 0; i < randomNumbers.length; i++) {

                long randomNum;
                do{
                    randomNum = (long)random.nextInt(20) + 1;
                }while(setNumbers.contains(randomNum));{
                    randomNumbers[i] = randomNum;
                    setNumbers.add(randomNum);

                    personalizeShopDTOS.add(repository.ShopIdByPersonalizeShop(randomNumbers[i]));
                    log.info("random number = {}",randomNum);
                }
                log.info("no Account! = {}", "존재하지 않는 회원입니다!");
            }
            return personalizeShopDTOS;
        }


        // Personalize 서비스 클라이언트 생성
        PersonalizeRuntimeClient personalizeClient = PersonalizeRuntimeClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();



        String userId = String.valueOf(UserId);
        // GetRecommendations 요청 생성
        GetRecommendationsRequest request = GetRecommendationsRequest.builder()
                .campaignArn("arn:aws:personalize:ap-northeast-2:556683426101:campaign/noye-s-team-compaign")
                .numResults(8)
                .userId(userId)
                .build();

        // GetRecommendations 요청 보내기
        GetRecommendationsResponse response = personalizeClient.getRecommendations(request);

        List<PredictedItem> items = response.itemList();

        CustomerPersonalizeShopDTO dto = new CustomerPersonalizeShopDTO();

        for (PredictedItem item: items) {

            Long shopId = Long.valueOf(item.itemId());

            log.info("UserId = {}",UserId);
            log.info("Response ShopId = {}",shopId);

            dto = repository.ShopIdByPersonalizeShop(shopId);

            if (dto != null){

                personalizeShopDTOS.add(dto);

                log.info("ShopId = {}",dto.getShopId());
                log.info("ShopName = {}",dto.getShopName());
                log.info("ShopStatus = {}",dto.getShopInfo());
                log.info("BranchId = {}",dto.getBranchId());
            }
        }
        return personalizeShopDTOS;
    }

}
