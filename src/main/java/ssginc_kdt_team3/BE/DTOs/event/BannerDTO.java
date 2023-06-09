package ssginc_kdt_team3.BE.DTOs.event;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssginc_kdt_team3.BE.domain.Event;

@Data
@NoArgsConstructor
public class BannerDTO {

    private Long id;

    private String bannerUrl;

    public BannerDTO(Event event) {
        this.id = event.getId();
        this.bannerUrl = event.getBannerUrl();
    }
}
