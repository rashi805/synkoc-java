package in.synkoc.campusevents;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CourseDataInitializer implements ApplicationRunner {

	private final CourseRepository courseRepository;

	public CourseDataInitializer(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	@Override
	public void run(ApplicationArguments args) {
		if (courseRepository.count() == 0) {
			courseRepository.saveAll(List.of(
					new Course("Java Full Stack", new BigDecimal("14999"), 6, "beginner"),
					new Course("Data Science", new BigDecimal("12999"), 6, "intermediate"),
					new Course("Cloud Basics", new BigDecimal("4999"), 2, "beginner")));
		}
	}
}
