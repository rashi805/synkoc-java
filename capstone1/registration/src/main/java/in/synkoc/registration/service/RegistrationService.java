package in.synkoc.registration.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.synkoc.registration.exception.DuplicateRegistrationException;
import in.synkoc.registration.exception.EventFullException;
import in.synkoc.registration.exception.EventNotFoundException;
import in.synkoc.registration.exception.PastEventRegistrationException;
import in.synkoc.registration.exception.RegistrationNotFoundException;
import in.synkoc.registration.form.RegistrationForm;
import in.synkoc.registration.model.Event;
import in.synkoc.registration.model.Registration;
import in.synkoc.registration.repository.EventRepository;
import in.synkoc.registration.repository.RegistrationRepository;

@Service
public class RegistrationService {

	private final EventRepository eventRepository;
	private final RegistrationRepository registrationRepository;

	public RegistrationService(EventRepository eventRepository, RegistrationRepository registrationRepository) {
		this.eventRepository = eventRepository;
		this.registrationRepository = registrationRepository;
	}

	@Transactional
	public Registration register(Long eventId, RegistrationForm form) {
		Event event = eventRepository.findByIdForUpdate(eventId)
				.orElseThrow(() -> new EventNotFoundException(eventId));
		if (event.getDate().isBefore(LocalDate.now())) {
			throw new PastEventRegistrationException();
		}
		String email = form.getEmail().trim().toLowerCase(Locale.ROOT);

		if (registrationRepository.existsByEvent_IdAndEmailIgnoreCase(eventId, email)) {
			throw new DuplicateRegistrationException();
		}
		if (registrationRepository.countByEvent_Id(eventId) >= event.getCapacity()) {
			throw new EventFullException();
		}

		Registration registration = new Registration(event, form.getName().trim(), email,
				form.getCollege().trim(), form.getYearOfStudy());
		return registrationRepository.save(registration);
	}

	@Transactional(readOnly = true)
	public Registration getRegistration(Long id) {
		return registrationRepository.findById(id)
				.orElseThrow(() -> new RegistrationNotFoundException(id));
	}

	@Transactional(readOnly = true)
	public List<Registration> registrationsForEvent(Long eventId) {
		if (!eventRepository.existsById(eventId)) {
			throw new EventNotFoundException(eventId);
		}
		return registrationRepository.findByEvent_IdOrderByIdAsc(eventId);
	}
}
