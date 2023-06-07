package ssginc_kdt_team3.BE.DTOs.shop;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ssginc_kdt_team3.BE.enums.ShopCategory;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ShopAddDTO {

    @NotNull
    private String businessCeo; // 사업장 등록주

    @NotNull
    @Length(min = 10, max = 10)
    private String businessNumber; // 사업장 등록번호

    @NotNull
    private String shopName; // 매장 이름

    @NotNull
    private String phone;

    @NotNull
    @Pattern(regexp = "^[A-Z]{1}[0-9]{2}$")
    private String location; //지점 내 위치

    @NotNull
    @Length(max = 50)
    private String shopInfo; // 매장 설명

    @NotNull
    private Long branchId; // 브랜치 id

    @NotNull
    private Long ownerId; // 점주 id  -> name 가져오기

    @NotNull
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
    private String openDay; // 개업일

    @NotNull
    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$")
    private String openTime;  // 오픈 시간

    @NotNull
    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$")
    private String orderCloseTime; // 마감 시간

    @NotNull
    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$")
    private String closeTime; // 문닫는 시간

    @NotNull
    private int seat;

    @NotNull
    private ShopCategory shopCategory;
}
