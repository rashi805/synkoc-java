package in.synkoc.events;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

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

	private final EventRepository eventRepository;

	public EventController(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@GetMapping
	public List<Event> all(@RequestParam(required = false) String city,
			@RequestParam(required = false) Integer minSeats) {
		Stream<Event> events = eventRepository.findAll().stream();
		if (city != null && !city.isBlank()) {
			String normalizedCity = city.trim().toLowerCase(Locale.ROOT);
			events = events.filter(event -> event.getCity().toLowerCase(Locale.ROOT).equals(normalizedCity));
		}
		if (minSeats != null) {
			events = events.filter(event -> event.getSeats() >= minSeats);
		}
		return events.sorted(Comparator.comparing(Event::getId)).toList();
	}

	@GetMapping("/search")
	public List<Event> search(@RequestParam String q) {
		if (q == null || q.isBlank()) {
			return List.of();
		}
		String keyword = q.trim().toLowerCase(Locale.ROOT);
		return eventRepository.findAll().stream()
				.filter(event -> event.getName().toLowerCase(Locale.ROOT).contains(keyword))
				.sorted(Comparator.comparing(Event::getName, String.CASE_INSENSITIVE_ORDER))
				.toList();
	}

	@GetMapping("/stats")
	public List<CityStats> stats() {
		return eventRepository.findCityStats().stream()
				.sorted(Comparator.comparingLong(CityStats::totalSeats).reversed().thenComparing(CityStats::city))
				.toList();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Event> one(@PathVariable Long id) {
		return eventRepository.findById(id).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Event> create(@Valid @RequestBody EventRequest request) {
		Event event = eventRepository.save(new Event(request.name().trim(), request.city().trim(), request.seats()));
		return ResponseEntity.created(URI.create("/api/events/" + event.getId())).body(event);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Event> replace(@PathVariable Long id, @Valid @RequestBody EventRequest request) {
		return eventRepository.findById(id).map(event -> {
			event.replaceWith(request);
			return ResponseEntity.ok(eventRepository.save(event));
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!eventRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		eventRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
