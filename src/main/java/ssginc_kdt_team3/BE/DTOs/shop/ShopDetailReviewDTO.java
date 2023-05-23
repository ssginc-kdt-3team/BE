package ssginc_kdt_team3.BE.DTOs.shop;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import ssginc_kdt_team3.BE.DTOs.customer.ReviewResponseDTO;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.domain.ShopMenu;
import ssginc_kdt_team3.BE.enums.ShopStatus;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ShopDetailReviewDTO { // 매장 상세페이지 하단 후기 DTO
  Page<ReviewResponseDTO> reviewDto;

  public ShopDetailReviewDTO(Page<ReviewResponseDTO> reviewDto) { //서비스에서 여기다 값을 담아줄거야
    this.reviewDto = reviewDto;
  }
}
