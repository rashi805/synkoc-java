package in.synkoc.events;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EventServiceTest {

	@Autowired
	private EventService eventService;

	@Autowired
	private EventRepository eventRepository;

	@BeforeEach
	void reset() {
		eventRepository.deleteAll();
		eventRepository.save(new Event("Hackathon", "Pune", 120));
		eventRepository.save(new Event("Robotics Workshop", "Mumbai", 40));
	}

	@Test
	void createsEventWithPositiveGeneratedId() {
		EventResponse created = eventService.create(new EventRequest("Career Fair", "Delhi", 80));
		assertThat(created.id()).isPositive();
		assertThat(eventRepository.findById(created.id())).isPresent();
	}

	@Test
	void trimsNameAndCityWhenSaving() {
		EventResponse created = eventService.create(new EventRequest("  Resume Clinic  ", " Pune ", 20));
		assertThat(created.name()).isEqualTo("Resume Clinic");
		assertThat(created.city()).isEqualTo("Pune");
	}

	@Test
	void rejectsDuplicateNamesWithoutConsideringCapitalization() {
		assertThatThrownBy(() -> eventService.create(new EventRequest("hAcKaThOn", "Pune", 25)))
				.isInstanceOf(DuplicateEventException.class);
		assertThat(eventRepository.count()).isEqualTo(2);
	}

	@Test
	void missingIdThrowsTheExpectedExceptionMessage() {
		assertThatThrownBy(() -> eventService.get(Long.MAX_VALUE))
				.isInstanceOf(EventNotFoundException.class)
				.hasMessage("Event " + Long.MAX_VALUE + " was not found.");
	}

	@Test
	void updateKeepsTheOriginalIdentifier() {
		Long id = eventRepository.findAll().getFirst().getId();
		EventResponse updated = eventService.replace(id, new EventRequest("Updated", "Delhi", 70));
		assertThat(updated.id()).isEqualTo(id);
		assertThat(updated.name()).isEqualTo("Updated");
	}

	@Test
	void cityFilterIsCaseInsensitiveAndTrimsWhitespace() {
		assertThat(eventService.list("  pUnE ", null))
				.extracting(EventResponse::city)
				.containsExactly("Pune");
	}
}
