package in.synkoc.registration.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import in.synkoc.registration.service.EventService;
import in.synkoc.registration.service.RegistrationService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final EventService eventService;
	private final RegistrationService registrationService;

	public AdminController(EventService eventService, RegistrationService registrationService) {
		this.eventService = eventService;
		this.registrationService = registrationService;
	}

	@GetMapping("/events")
	public String listEvents(Model model) {
		model.addAttribute("events", eventService.adminEvents());
		return "admin/events";
	}

	@GetMapping("/events/{id}/registrations")
	public String listRegistrations(@PathVariable Long id, Model model) {
		model.addAttribute("event", eventService.getEvent(id));
		model.addAttribute("registrations", registrationService.registrationsForEvent(id));
		return "admin/registrations";
	}
}
