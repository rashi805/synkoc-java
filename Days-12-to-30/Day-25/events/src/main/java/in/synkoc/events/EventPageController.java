package in.synkoc.events;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/events")
public class EventPageController {

	private final EventService eventService;

	public EventPageController(EventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping
	public String list(Model model) {
		model.addAttribute("events", eventService.list(null, null));
		return "events/list";
	}

	@GetMapping("/new")
	public String newForm(Model model) {
		model.addAttribute("eventForm", new EventForm());
		model.addAttribute("formTitle", "Create event");
		return "events/form";
	}

	@PostMapping
	public String create(@Valid @ModelAttribute("eventForm") EventForm form, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		if (!result.hasErrors()) {
			try {
				eventService.create(form.toRequest());
			} catch (DuplicateEventException exception) {
				result.rejectValue("name", "duplicate", "An event with this name already exists.");
			}
		}
		if (result.hasErrors()) {
			model.addAttribute("formTitle", "Create event");
			return "events/form";
		}
		redirectAttributes.addFlashAttribute("success", "Event created successfully.");
		return "redirect:/events";
	}

	@GetMapping("/{id}")
	public String detail(@PathVariable Long id, Model model) {
		model.addAttribute("event", eventService.get(id));
		return "events/detail";
	}

	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable Long id, Model model) {
		EventResponse event = eventService.get(id);
		model.addAttribute("eventForm", new EventForm(event.name(), event.city(), event.seats()));
		model.addAttribute("formTitle", "Edit event");
		model.addAttribute("eventId", event.id());
		return "events/form";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable Long id, @Valid @ModelAttribute("eventForm") EventForm form,
			BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		if (!result.hasErrors()) {
			try {
				eventService.replace(id, form.toRequest());
			} catch (DuplicateEventException exception) {
				result.rejectValue("name", "duplicate", "An event with this name already exists.");
			}
		}
		if (result.hasErrors()) {
			model.addAttribute("formTitle", "Edit event");
			model.addAttribute("eventId", id);
			return "events/form";
		}
		redirectAttributes.addFlashAttribute("success", "Event updated successfully.");
		return "redirect:/events/" + id;
	}
}
