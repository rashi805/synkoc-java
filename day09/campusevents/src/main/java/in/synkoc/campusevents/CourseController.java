package in.synkoc.campusevents;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${synkoc.api.path}")
public class CourseController {

	private static final BigDecimal ZERO_FEE = BigDecimal.ZERO;

	private final String apiPath;
	private final CourseRepository courseRepository;

	public CourseController(@Value("${synkoc.api.path}") String apiPath, CourseRepository courseRepository) {
		this.apiPath = apiPath;
		this.courseRepository = courseRepository;
	}

	@GetMapping
	public List<Course> all() {
		return courseRepository.findAllByOrderByIdAsc();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Course> one(@PathVariable long id) {
		return courseRepository.findById(id).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody CourseRequest request) {
		if (request == null) {
			return ResponseEntity.badRequest().body(Map.of("error", "Course details are required"));
		}
		if (request.title() == null || request.title().isBlank()) {
			return ResponseEntity.badRequest().body(Map.of("error", "Title is required"));
		}
		if (request.fee() == null || request.fee().compareTo(ZERO_FEE) < 0) {
			return ResponseEntity.badRequest().body(Map.of("error", "Fee must be at least 0"));
		}
		if (request.weeks() == null) {
			return ResponseEntity.badRequest().body(Map.of("error", "Weeks is required"));
		}
		int weeks;
		try {
			weeks = request.weeks().intValueExact();
		} catch (ArithmeticException exception) {
			return ResponseEntity.badRequest().body(Map.of("error", "Weeks must be an integer"));
		}
		if (request.level() == null || request.level().isBlank()) {
			return ResponseEntity.badRequest().body(Map.of("error", "Level is required"));
		}

		Course course = courseRepository.save(new Course(request.title(), request.fee(), weeks, request.level()));
		return ResponseEntity.created(URI.create(apiPath + "/" + course.getId())).body(course);
	}
}
