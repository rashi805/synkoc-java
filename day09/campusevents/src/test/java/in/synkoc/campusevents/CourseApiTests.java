package in.synkoc.campusevents;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CourseApiTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CourseRepository courseRepository;

	@BeforeEach
	void resetCourses() {
		courseRepository.deleteAll();
		courseRepository.save(new Course("Java Full Stack", new BigDecimal("14999"), 6, "beginner"));
	}

	@Test
	void listsPersistedCoursesInIdOrder() throws Exception {
		mockMvc.perform(get("/api/courses"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("Java Full Stack"))
				.andExpect(jsonPath("$[0].weeks").value(6));
	}

	@Test
	void returnsNotFoundForUnknownCourse() throws Exception {
		mockMvc.perform(get("/api/courses/999999"))
				.andExpect(status().isNotFound());
	}

	@Test
	void createsAndPersistsCourseWithLocationHeader() throws Exception {
		mockMvc.perform(post("/api/courses")
						.contentType("application/json")
						.content("""
								{"title":"Mobile App Basics","fee":8999,"weeks":4,"level":"beginner"}
								"""))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", org.hamcrest.Matchers.matchesPattern("/api/courses/\\d+")))
				.andExpect(jsonPath("$.title").value("Mobile App Basics"))
				.andExpect(jsonPath("$.id").isNumber());

		org.assertj.core.api.Assertions.assertThat(courseRepository.count()).isEqualTo(2);
	}

	@Test
	void rejectsMissingCourseFields() throws Exception {
		mockMvc.perform(post("/api/courses")
						.contentType("application/json")
						.content("""
								{"title":" ","fee":1,"weeks":1,"level":"beginner"}
								"""))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Title is required")));
		org.assertj.core.api.Assertions.assertThat(courseRepository.count()).isEqualTo(1);
	}

	@Test
	void originalSampleConfigurationIsPreserved() {
		org.assertj.core.api.Assertions.assertThat(courseRepository.findAll())
				.extracting(Course::getTitle)
				.containsExactly("Java Full Stack");
	}
}
