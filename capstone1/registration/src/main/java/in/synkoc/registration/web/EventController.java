package in.synkoc.registration.web;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import in.synkoc.registration.dto.EventSummary;
import in.synkoc.registration.service.EventService;

@Controller
@RequestMapping("/events")
public class EventController {

	private final EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping
	public String listEvents(Model model) {
		model.addAttribute("events", eventService.upcomingEvents());
		return "events/list";
	}

	@GetMapping("/{id}")
	public String showEvent(@PathVariable Long id, Model model) {
		EventSummary event = eventService.toSummary(eventService.getEvent(id));
		model.addAttribute("event", event);
		model.addAttribute("registrationOpen",
				!event.date().isBefore(LocalDate.now()) && event.seatsLeft() > 0);
		return "events/detail";
	}
}
