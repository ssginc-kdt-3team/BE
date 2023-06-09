package ssginc_kdt_team3.BE.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerJoinDTO;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerLoginDTO;
import ssginc_kdt_team3.BE.DTOs.customer.EmailFindDTO;
import ssginc_kdt_team3.BE.DTOs.customer.PasswordFindDTO;
import ssginc_kdt_team3.BE.DTOs.event.BannerDTO;
import ssginc_kdt_team3.BE.DTOs.event.EventDetailDTO;
import ssginc_kdt_team3.BE.domain.Customer;
import ssginc_kdt_team3.BE.domain.Event;
import ssginc_kdt_team3.BE.domain.Grade;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.exception.ErrorResponse;
import ssginc_kdt_team3.BE.repository.customer.JpaDataCustomerRepository;
import ssginc_kdt_team3.BE.service.customer.CustomerService;
import ssginc_kdt_team3.BE.service.customer.KakaoService;
import ssginc_kdt_team3.BE.service.event.EventService;
import ssginc_kdt_team3.BE.service.pointManagement.PointManagementService;
import ssginc_kdt_team3.BE.util.TimeUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

