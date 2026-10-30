package in.synkoc.registration.data;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.synkoc.registration.model.Event;
import in.synkoc.registration.repository.EventRepository;

@Component
public class EventDataInitializer implements ApplicationRunner {

	private final EventRepository eventRepository;

	public EventDataInitializer(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@Override
	public void run(ApplicationArguments args) {
		if (eventRepository.count() > 0) {
			return;
		}

		LocalDate today = LocalDate.now();
		eventRepository.saveAll(List.of(
				new Event("Resume Clinic", "Innovation Hub", today.plusDays(7), 2),
				new Event("Spring Boot Study Jam", "Computer Science Lab", today.plusDays(14), 40),
				new Event("Campus Hackathon", "Main Auditorium", today.plusDays(21), 80),
				new Event("Design Thinking Workshop", "Learning Commons", today.plusDays(30), 30)));
	}
}
