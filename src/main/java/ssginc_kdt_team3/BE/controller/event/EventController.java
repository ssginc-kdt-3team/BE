package ssginc_kdt_team3.BE.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.event.BannerDTO;
import ssginc_kdt_team3.BE.DTOs.event.EventDetailDTO;
import ssginc_kdt_team3.BE.service.event.EventService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {

  private final EventService eventService;

  @GetMapping("/banners")
  public ResponseEntity<List<BannerDTO>> homePageBanner() {
    List<BannerDTO> bannerDTOS = eventService.homeBanner();

    return ResponseEntity.ok(bannerDTOS);
  }

  @GetMapping("/{id}")
  public ResponseEntity eventDetail(@PathVariable(name = "id") Long eventId) {
    Optional<EventDetailDTO> eventDetailDTO = eventService.showEventDetail(eventId);

    if (eventDetailDTO.isPresent()) {
      EventDetailDTO getEventDetailDTO = eventDetailDTO.get();

      return ResponseEntity.ok(getEventDetailDTO);
    }

    return ResponseEntity.badRequest().body("존재하지 않는 이벤트 id 입니다.");
  }


}

