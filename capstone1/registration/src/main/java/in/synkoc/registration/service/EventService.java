package in.synkoc.registration.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.synkoc.registration.dto.AdminEventSummary;
import in.synkoc.registration.dto.EventSummary;
import in.synkoc.registration.exception.EventNotFoundException;
import in.synkoc.registration.model.Event;
import in.synkoc.registration.repository.EventRepository;
import in.synkoc.registration.repository.RegistrationRepository;

@Service
public class EventService {

	private final EventRepository eventRepository;
	private final RegistrationRepository registrationRepository;

	public EventService(EventRepository eventRepository, RegistrationRepository registrationRepository) {
		this.eventRepository = eventRepository;
		this.registrationRepository = registrationRepository;
	}

	@Transactional(readOnly = true)
	public List<EventSummary> upcomingEvents() {
		return eventRepository.findByDateGreaterThanEqualOrderByDateAsc(LocalDate.now()).stream()
				.map(this::toSummary)
				.toList();
	}

	@Transactional(readOnly = true)
	public List<AdminEventSummary> adminEvents() {
		return eventRepository.findAllByOrderByDateAsc().stream()
				.map(event -> new AdminEventSummary(event.getId(), event.getTitle(), event.getDate(),
						registrationRepository.countByEventId(event.getId()), event.getCapacity()))
				.toList();
	}

	@Transactional(readOnly = true)
	public Event getEvent(Long id) {
		return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
	}

	@Transactional(readOnly = true)
	public EventSummary toSummary(Event event) {
		long registered = registrationRepository.countByEventId(event.getId());
		return new EventSummary(event.getId(), event.getTitle(), event.getDate(), event.getVenue(),
				event.getCapacity(), Math.max(0, event.getCapacity() - registered));
	}
}
