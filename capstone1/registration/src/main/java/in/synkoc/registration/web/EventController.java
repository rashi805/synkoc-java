package in.synkoc.registration.web;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String listEvents(@RequestParam(required = false) String query, Model model) {
		String searchQuery = query == null ? "" : query.trim();
		model.addAttribute("events", eventService.searchUpcomingEvents(searchQuery));
		model.addAttribute("query", searchQuery);
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
