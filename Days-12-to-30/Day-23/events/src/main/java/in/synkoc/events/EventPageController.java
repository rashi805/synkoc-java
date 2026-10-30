package in.synkoc.events;

import java.util.Comparator;
import java.util.List;

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

	private final EventRepository eventRepository;

	public EventPageController(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@GetMapping
	public String list(Model model) {
		List<Event> events = eventRepository.findAll().stream()
				.sorted(Comparator.comparing(Event::getId))
				.toList();
		model.addAttribute("events", events);
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
		if (!result.hasErrors() && eventRepository.existsByNameIgnoreCase(form.getName().trim())) {
			result.rejectValue("name", "duplicate", "An event with this name already exists.");
		}
		if (result.hasErrors()) {
			model.addAttribute("formTitle", "Create event");
			return "events/form";
		}
		eventRepository.save(new Event(form.getName().trim(), form.getCity().trim(), form.getSeats()));
		redirectAttributes.addFlashAttribute("success", "Event created successfully.");
		return "redirect:/events";
	}

	@GetMapping("/{id}")
	public String detail(@PathVariable Long id, Model model) {
		model.addAttribute("event", getEvent(id));
		return "events/detail";
	}

	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable Long id, Model model) {
		Event event = getEvent(id);
		model.addAttribute("eventForm", new EventForm(event.getName(), event.getCity(), event.getSeats()));
		model.addAttribute("formTitle", "Edit event");
		model.addAttribute("eventId", event.getId());
		return "events/form";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable Long id, @Valid @ModelAttribute("eventForm") EventForm form,
			BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		Event event = getEvent(id);
		if (!result.hasErrors() && eventRepository.existsByNameIgnoreCaseAndIdNot(form.getName().trim(), id)) {
			result.rejectValue("name", "duplicate", "An event with this name already exists.");
		}
		if (result.hasErrors()) {
			model.addAttribute("formTitle", "Edit event");
			model.addAttribute("eventId", id);
			return "events/form";
		}
		event.replaceWith(form.toRequest());
		eventRepository.save(event);
		redirectAttributes.addFlashAttribute("success", "Event updated successfully.");
		return "redirect:/events/" + id;
	}

	private Event getEvent(Long id) {
		return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
	}
}
