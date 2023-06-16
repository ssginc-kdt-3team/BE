package ssginc_kdt_team3.BE.DTOs.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPersonalizeShopDTO {

    private Long shopId;

    private String shopName;

    private String shopImgUrl;

    private String shopInfo;

    private Long branchId;

}
