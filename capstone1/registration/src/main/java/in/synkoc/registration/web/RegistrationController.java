package in.synkoc.registration.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.synkoc.registration.dto.EventSummary;
import in.synkoc.registration.exception.DuplicateRegistrationException;
import in.synkoc.registration.exception.EventFullException;
import in.synkoc.registration.form.RegistrationForm;
import in.synkoc.registration.model.Registration;
import in.synkoc.registration.service.EventService;
import in.synkoc.registration.service.RegistrationService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/events/{eventId}/register")
public class RegistrationController {

	private final EventService eventService;
	private final RegistrationService registrationService;

	public RegistrationController(EventService eventService, RegistrationService registrationService) {
		this.eventService = eventService;
		this.registrationService = registrationService;
	}

	@GetMapping
	public String showForm(@PathVariable Long eventId, Model model) {
		EventSummary event = eventService.toSummary(eventService.getEvent(eventId));
		model.addAttribute("event", event);
		model.addAttribute("registrationForm", new RegistrationForm());
		return "registrations/form";
	}

	@PostMapping
	public String register(@PathVariable Long eventId,
			@Valid @ModelAttribute("registrationForm") RegistrationForm form,
			BindingResult bindingResult, Model model) {
		EventSummary event = eventService.toSummary(eventService.getEvent(eventId));
		if (bindingResult.hasErrors()) {
			model.addAttribute("event", event);
			return "registrations/form";
		}
		try {
			Registration registration = registrationService.register(eventId, form);
			return "redirect:/registrations/" + registration.getId();
		} catch (DuplicateRegistrationException | EventFullException exception) {
			bindingResult.reject("registration.error", exception.getMessage());
			model.addAttribute("event", eventService.toSummary(eventService.getEvent(eventId)));
			return "registrations/form";
		}
	}

}
