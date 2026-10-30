package in.synkoc.events;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class EventDataInitializer implements ApplicationRunner {

	private final EventRepository eventRepository;

	public EventDataInitializer(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@Override
	public void run(ApplicationArguments args) {
		if (eventRepository.count() == 0) {
			eventRepository.saveAll(List.of(
					new Event("Hackathon", "Pune", 120),
					new Event("Robotics Workshop", "Mumbai", 40),
					new Event("Cultural Night", "Pune", 300),
					new Event("Career Fair", "Delhi", 80)));
		}
	}
}
