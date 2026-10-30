package in.synkoc.registration.web.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.synkoc.registration.dto.EventSummary;
import in.synkoc.registration.dto.RegistrationResponse;
import in.synkoc.registration.service.EventService;
import in.synkoc.registration.service.RegistrationService;

@RestController
@RequestMapping("/api/events")
public class EventApiController {

	private final EventService eventService;
	private final RegistrationService registrationService;

	public EventApiController(EventService eventService, RegistrationService registrationService) {
		this.eventService = eventService;
		this.registrationService = registrationService;
	}

	@GetMapping
	public List<EventSummary> listEvents() {
		return eventService.upcomingEvents();
	}

	@GetMapping("/{id}/registrations")
	public List<RegistrationResponse> listRegistrations(@PathVariable Long id) {
		return registrationService.registrationsForEvent(id).stream()
				.map(registration -> new RegistrationResponse(registration.getId(), registration.getName(),
						registration.getCollege(), registration.getYearOfStudy()))
				.toList();
	}
}
