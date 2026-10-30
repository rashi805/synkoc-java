package in.synkoc.registration.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import in.synkoc.registration.model.Registration;
import in.synkoc.registration.service.EventService;
import in.synkoc.registration.service.RegistrationService;

@Controller
public class ConfirmationController {

	private final EventService eventService;
	private final RegistrationService registrationService;

	public ConfirmationController(EventService eventService, RegistrationService registrationService) {
		this.eventService = eventService;
		this.registrationService = registrationService;
	}

	@GetMapping("/registrations/{id}")
	public String showConfirmation(@PathVariable Long id, Model model) {
		Registration registration = registrationService.getRegistration(id);
		model.addAttribute("registration", registration);
		model.addAttribute("event", eventService.getEvent(registration.getEventId()));
		return "registrations/confirmation";
	}
}
