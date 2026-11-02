package in.synkoc.registration.web.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.synkoc.registration.dto.EventSummary;
import in.synkoc.registration.dto.RegistrationResponse;
import in.synkoc.registration.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventApiController {

	private final EventService eventService;

	public EventApiController(EventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping
	public List<EventSummary> listEvents() {
		return eventService.upcomingEvents();
	}

	@GetMapping("/{id}")
	public EventSummary getEvent(@PathVariable Long id) {
		return eventService.toSummary(eventService.getEvent(id));
	}

	@GetMapping("/{id}/registrations")
	public List<RegistrationResponse> getRegistrations(@PathVariable Long id) {
		return eventService.registrationsForEvent(id);
	}
}
