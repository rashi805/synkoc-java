package in.synkoc.events;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventController {

	private final EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping
	public List<EventResponse> all(@RequestParam(required = false) String city,
			@RequestParam(required = false) Integer minSeats) {
		return eventService.list(city, minSeats);
	}

	@GetMapping("/search")
	public List<EventResponse> search(@RequestParam(defaultValue = "") String q) {
		return eventService.search(q);
	}

	@GetMapping("/stats")
	public List<CityStats> stats() {
		return eventService.stats();
	}

	@GetMapping("/{id}")
	public EventResponse one(@PathVariable Long id) {
		return eventService.get(id);
	}

	@PostMapping
	public ResponseEntity<EventResponse> create(@Valid @RequestBody EventRequest request) {
		EventResponse created = eventService.create(request);
		return ResponseEntity.created(URI.create("/api/events/" + created.id())).body(created);
	}

	@PutMapping("/{id}")
	public EventResponse replace(@PathVariable Long id, @Valid @RequestBody EventRequest request) {
		return eventService.replace(id, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		eventService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
