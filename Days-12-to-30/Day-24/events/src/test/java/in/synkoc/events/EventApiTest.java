package in.synkoc.events;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class EventApiTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EventRepository eventRepository;

	private Long eventId;

	@BeforeEach
	void reset() {
		eventRepository.deleteAll();
		eventId = eventRepository.save(new Event("Hackathon", "Pune", 120)).getId();
		eventRepository.save(new Event("Robotics Workshop", "Mumbai", 40));
	}

	@Test
	void createsEventWithCreatedStatusAndLocation() throws Exception {
		mockMvc.perform(post("/api/events")
						.contentType(MediaType.APPLICATION_JSON)
						.content(validEvent("Career Fair", "Delhi", 80)))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", org.hamcrest.Matchers.matchesPattern("/api/events/\\d+")))
				.andExpect(jsonPath("$.name").value("Career Fair"))
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.email").doesNotExist());
	}

	@Test
	void invalidCreateReturnsProblemDetailAndFieldErrors() throws Exception {
		mockMvc.perform(post("/api/events")
						.contentType(MediaType.APPLICATION_JSON)
						.content(validEvent(" ", "", 0)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/problem+json"))
				.andExpect(jsonPath("$.title").value("Validation failed"))
				.andExpect(jsonPath("$.errors.name").exists())
				.andExpect(jsonPath("$.errors.city").exists())
				.andExpect(jsonPath("$.errors.seats").exists());
	}

	@Test
	void unknownEventReturnsNotFoundProblem() throws Exception {
		mockMvc.perform(get("/api/events/999999"))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType("application/problem+json"))
				.andExpect(jsonPath("$.status").value(404));
	}

	@Test
	void duplicateNamesConflictRegardlessOfCase() throws Exception {
		mockMvc.perform(post("/api/events")
						.contentType(MediaType.APPLICATION_JSON)
						.content(validEvent("hAcKaThOn", "Pune", 90)))
				.andExpect(status().isConflict())
				.andExpect(content().contentType("application/problem+json"))
				.andExpect(jsonPath("$.title").value("Duplicate event"));
	}

	@Test
	void replacesEventWithoutChangingItsId() throws Exception {
		mockMvc.perform(put("/api/events/{id}", eventId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(validEvent("Hackathon Updated", "Delhi", 70)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(eventId))
				.andExpect(jsonPath("$.name").value("Hackathon Updated"))
				.andExpect(jsonPath("$.city").value("Delhi"));

		assertThat(eventRepository.findById(eventId).orElseThrow().getId()).isEqualTo(eventId);
	}

	@Test
	void deletesEventAndReturnsNotFoundAfterward() throws Exception {
		mockMvc.perform(delete("/api/events/{id}", eventId))
				.andExpect(status().isNoContent());
		mockMvc.perform(get("/api/events/{id}", eventId))
				.andExpect(status().isNotFound());
	}

	@Test
	void filtersCityIgnoringCaseAndWhitespaceAndEmptyCityReturnsAll() throws Exception {
		mockMvc.perform(get("/api/events").param("city", "  pUnE "))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].city").value("Pune"));
		mockMvc.perform(get("/api/events").param("city", " "))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2));
	}

	@Test
	void rejectsMalformedJsonAndInvalidNumericPathAsProblemJson() throws Exception {
		mockMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON).content("{"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/problem+json"));
		mockMvc.perform(get("/api/events/not-a-number"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/problem+json"));
	}

	private String validEvent(String name, String city, int seats) {
		return "{\"name\":\"" + name + "\",\"city\":\"" + city + "\",\"seats\":" + seats + "}";
	}
}
