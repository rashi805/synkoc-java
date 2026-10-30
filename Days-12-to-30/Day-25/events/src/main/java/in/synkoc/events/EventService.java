package in.synkoc.events;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

	private final EventRepository eventRepository;

	public EventService(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@Transactional(readOnly = true)
	public List<EventResponse> list(String city, Integer minimumSeats) {
		return eventRepository.findAll().stream()
				.filter(event -> city == null || city.isBlank()
						|| event.getCity().equalsIgnoreCase(city.trim()))
				.filter(event -> minimumSeats == null || event.getSeats() >= minimumSeats)
				.sorted(Comparator.comparing(Event::getId))
				.map(EventResponse::from)
				.toList();
	}

	@Transactional(readOnly = true)
	public List<CityStats> stats() {
		return eventRepository.findCityStats().stream()
				.sorted(Comparator.comparingLong(CityStats::totalSeats).reversed().thenComparing(CityStats::city))
				.toList();
	}

	@Transactional(readOnly = true)
	public EventResponse get(Long id) {
		return EventResponse.from(findEvent(id));
	}

	@Transactional
	public EventResponse create(EventRequest request) {
		String name = request.name().trim();
		if (eventRepository.existsByNameIgnoreCase(name)) {
			throw new DuplicateEventException(name);
		}
		Event event = eventRepository.save(new Event(name, request.city().trim(), request.seats()));
		return EventResponse.from(event);
	}

	@Transactional
	public EventResponse replace(Long id, EventRequest request) {
		Event event = findEvent(id);
		String name = request.name().trim();
		if (eventRepository.existsByNameIgnoreCaseAndIdNot(name, id)) {
			throw new DuplicateEventException(name);
		}
		event.replaceWith(new EventRequest(name, request.city().trim(), request.seats()));
		return EventResponse.from(eventRepository.save(event));
	}

	@Transactional
	public void delete(Long id) {
		eventRepository.delete(findEvent(id));
	}

	@Transactional(readOnly = true)
	public List<EventResponse> search(String keyword) {
		if (keyword == null || keyword.isBlank()) {
			return List.of();
		}
		String searchTerm = keyword.trim().toLowerCase(Locale.ROOT);
		return eventRepository.findAll().stream()
				.filter(event -> event.getName().toLowerCase(Locale.ROOT).contains(searchTerm))
				.sorted(Comparator.comparing(Event::getName, String.CASE_INSENSITIVE_ORDER))
				.map(EventResponse::from)
				.toList();
	}

	private Event findEvent(Long id) {
		return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
	}
}
