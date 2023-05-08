package ssginc_kdt_team3.BE.DTOs.shop;

import ssginc_kdt_team3.BE.domain.ShopOperationInfo;
import ssginc_kdt_team3.BE.domain.Store;

import java.time.LocalTime;

public class ShopListDTO {

    private long id;

    private String name;

    private UserStatus status;

    private String location;

    private String business_name;

    private ShopOperationInfo operationInfo;

    private LocalTime open_time;

    private LocalTime close_time;

    private LocalTime order_close;
}
