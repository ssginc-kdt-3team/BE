package ssginc_kdt_team3.BE.service.Shop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.DTOs.shop.ShopDetailDTO;

import ssginc_kdt_team3.BE.repository.shop.JpaDataShopRepository;
import ssginc_kdt_team3.BE.repository.shop.ShopRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopDetailService {

    private final ShopRepository repo;

    public String ShopDetailList(long id) throws JsonProcessingException {

        List<ShopDetailDTO> DetailDTO = repo.ShopDetailJoin();

        List<ShopDetailDTO> ResultDTO = new ArrayList<>();

        for (ShopDetailDTO shop: DetailDTO) {
            if (shop.getShopId() != id){
                continue;
            }
            ShopDetailDTO dd = new ShopDetailDTO();

            dd.setShopId(shop.getShopId());
            dd.setShopName(shop.getShopName());
            dd.setShopLocation(shop.getShopLocation());
            dd.setShopImg(shop.getShopImg());
            dd.setShopStatus(shop.getShopStatus());
            dd.setShopInfo(shop.getShopInfo());
            //
            dd.setShopOpenTime(shop.getShopOpenTime());
            dd.setShopCloseTime(shop.getShopCloseTime());
            //
            dd.setMenuId(shop.getMenuId());
            dd.setMenuImg(shop.getMenuImg());
            dd.setMenuName(shop.getMenuName());
            dd.setMenuPrice(shop.getMenuPrice());
//            dd.setReviewId(shop.getReviewId());
//            dd.setReviewTitle(shop.getReviewTitle());
//            dd.setReviewContents(shop.getReviewContents());
//            dd.setReviewTime(shop.getReviewTime());

            ResultDTO.add(dd);
        }
        String JsonList = DTOJson(ResultDTO);
        return JsonList;
    }
    public String DTOJson(List<ShopDetailDTO> dto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dto);
    }
}
