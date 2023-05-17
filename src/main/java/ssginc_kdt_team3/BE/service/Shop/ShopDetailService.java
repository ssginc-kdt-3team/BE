package ssginc_kdt_team3.BE.service.Shop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.DTOs.shop.ShopDetailDTO;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.domain.ShopMenu;
import ssginc_kdt_team3.BE.domain.ShopOperationInfo;
import ssginc_kdt_team3.BE.repository.shop.JpaDataShopRepository;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopDetailService {

    private final JpaDataShopRepository repo;
//    private final DataOwnerRepository repo;

    public String ShopDetailList(Long id) throws JsonProcessingException {

        List<Object> DetailList = repo.DetailList(id);

        log.info("log info = {}",id);
        log.info("log info = {}",DetailList);

        if (DetailList.isEmpty()){
            throw new NullPointerException("잘못된 요청입니다.");
        }
        log.info("log.info= {}",123123);

        List<ShopDetailDTO> ResultDTO = new ArrayList<>();

        ShopDetailDTO dd = new ShopDetailDTO();

        for (Object shop : DetailList) {


            if (shop instanceof Shop){
                Shop shopObj = (Shop) shop;
                dd.setShopId(shopObj.getId());
                dd.setShopName(shopObj.getName());
                dd.setShopLocation(shopObj.getLocation());
                dd.setShopImg(shopObj.getShopImg());
                dd.setShopStatus(shopObj.getStatus());
                dd.setShopInfo(shopObj.getInfo());
            } else if (shop instanceof ShopMenu){
                ShopMenu MenuObj = (ShopMenu) shop;
                dd.setMenuId(MenuObj.getId());
                dd.setMenuImg(MenuObj.getMenu_img());
                dd.setMenuName(MenuObj.getName());
                dd.setMenuPrice(MenuObj.getPrice());
            } else if (shop instanceof ShopOperationInfo) {
                ShopOperationInfo infoObj = (ShopOperationInfo) shop;
                dd.setShopOpenTime(infoObj.getOpenTime());
                dd.setShopCloseTime(infoObj.getCloseTime());
            }
//            dd.setReviewId(shop.getReviewId());
//            dd.setReviewTitle(shop.getReviewTitle());
//            dd.setReviewContents(shop.getReviewContents());
//            dd.setReviewTime(shop.getReviewTime());
            ResultDTO.add(dd);
        }

        return DTOJson(ResultDTO);

    }
    public String DTOJson(List<ShopDetailDTO> dto) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        log.info("log.info = {}","=====JSON으로 변환완료=====");
        return mapper.writeValueAsString(dto);


    }
}
