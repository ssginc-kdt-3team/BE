package ssginc_kdt_team3.BE.DTOs.event;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.Event;
import ssginc_kdt_team3.BE.enums.EventStatus;
import ssginc_kdt_team3.BE.util.TimeUtils;

@Data
@NoArgsConstructor
public class EventDetailDTO {

    private Long id;

    private EventStatus status;

    private String title;

    private String bannerUrl;

    private String contentsUrl;

    private String startDate;

    private String endDate;

    private String branchName;

    public EventDetailDTO(Event event) {
        this.id = event.getId();
        this.status = event.getStatus();
        this.title = event.getTitle();
        this.bannerUrl = event.getBannerUrl();
        this.contentsUrl = event.getContentsUrl();
        this.startDate = TimeUtils.localDataTimeParseString(event.getStartDate());
        this.endDate = TimeUtils.localDataTimeParseString(event.getEndDate());
        this.branchName = event.getBranch().getName();
    }
}
