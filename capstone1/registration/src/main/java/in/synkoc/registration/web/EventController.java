package in.synkoc.registration.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
