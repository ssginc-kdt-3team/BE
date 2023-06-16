package ssginc_kdt_team3.BE.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import ssginc_kdt_team3.BE.domain.Event;
import ssginc_kdt_team3.BE.enums.EventStatus;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByStatusOrderByStartDateDesc(EventStatus status);
}
