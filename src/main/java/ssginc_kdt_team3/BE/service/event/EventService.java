package ssginc_kdt_team3.BE.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.event.BannerDTO;
import ssginc_kdt_team3.BE.DTOs.event.EventDetailDTO;
import ssginc_kdt_team3.BE.domain.Event;
import ssginc_kdt_team3.BE.enums.EventStatus;
import ssginc_kdt_team3.BE.repository.event.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional

public class EventService {

    private final EventRepository eventRepository;

    public List<BannerDTO> homeBanner() {
        List<BannerDTO> result = new ArrayList<>();
        List<Event> events = eventRepository.findAllByStatusOrderByStartDateDesc(EventStatus.PROCEEDING);

        for (Event e: events) {
            BannerDTO bannerDTO = new BannerDTO(e);
            result.add(bannerDTO);
        }

        return result;
    }

    public Optional<EventDetailDTO> showEventDetail(Long eventId) {
        Optional<Event> byId = eventRepository.findById(eventId);

        if (byId.isPresent()) {
            Event event = byId.get();
            EventDetailDTO eventDetailDTO = new EventDetailDTO(event);

            return Optional.ofNullable(eventDetailDTO);
        }

        return Optional.empty();
    }

}
