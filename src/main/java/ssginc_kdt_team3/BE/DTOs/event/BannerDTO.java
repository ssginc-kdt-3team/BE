package ssginc_kdt_team3.BE.DTOs.event;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.Event;
import ssginc_kdt_team3.BE.util.TimeUtils;

@Data
@NoArgsConstructor
public class BannerDTO {

    private Long id;

    private String bannerUrl;

    private String startDate;

    private String endDate;

    private String title;

    private String branchName;

    public BannerDTO(Event event) {
        this.id = event.getId();
        this.bannerUrl = event.getBannerUrl();
        this.startDate = TimeUtils.localDataTimeParseString(event.getStartDate());
        this.endDate = TimeUtils.localDataTimeParseString(event.getEndDate());
        this.title = event.getTitle();
        this.branchName = event.getBranch().getName();
    }
}
