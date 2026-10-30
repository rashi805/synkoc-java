package in.synkoc.events;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class EventsApplicationTests {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EventDataInitializer eventDataInitializer;

	@BeforeEach
	void resetSampleEvents() {
		eventRepository.deleteAll();
		eventRepository.save(new Event("Hackathon", "Pune", 120));
		eventRepository.save(new Event("Robotics Workshop", "Mumbai", 40));
		eventRepository.save(new Event("Cultural Night", "Pune", 300));
		eventRepository.save(new Event("Career Fair", "Delhi", 80));
	}

	@Test
	void seedsExactlyFourEventsOnlyWhenTheRepositoryIsEmpty() {
		assertThat(eventRepository.count()).isEqualTo(4);
		eventDataInitializer.run(null);
		assertThat(eventRepository.count()).isEqualTo(4);
	}

	@Test
	void filtersByCityAndMinimumSeats() throws Exception {
		mockMvc.perform(get("/api/events").param("city", "  PUNE ").param("minSeats", "100"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Hackathon"));
	}

	@Test
	void returnsCityStatsOrderedByTotalSeats() throws Exception {
		mockMvc.perform(get("/api/events/stats"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].city").value("Pune"))
				.andExpect(jsonPath("$[0].totalSeats").value(420));
	}

	@Test
	void rendersEventsListAndNewEventForm() throws Exception {
		mockMvc.perform(get("/events"))
				.andExpect(status().isOk())
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content()
						.string(org.hamcrest.Matchers.containsString("Hackathon")));
		mockMvc.perform(get("/events/new"))
				.andExpect(status().isOk())
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content()
						.string(org.hamcrest.Matchers.containsString("Create event")));
	}

	@Test
	void invalidFormShowsValidationErrorsWithoutSaving() throws Exception {
		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/events")
						.param("name", "")
						.param("city", "")
						.param("seats", "0"))
				.andExpect(status().isOk())
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content()
						.string(org.hamcrest.Matchers.containsString("Event name is required.")))
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content()
						.string(org.hamcrest.Matchers.containsString("Seats must be at least 1.")));
		assertThat(eventRepository.count()).isEqualTo(4);
	}

	@Test
	void validFormCreatesEventAndRedirects() throws Exception {
		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/events")
						.param("name", "Resume Clinic")
						.param("city", "Pune")
						.param("seats", "25"))
				.andExpect(status().is3xxRedirection())
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl("/events"));
		assertThat(eventRepository.existsByNameIgnoreCase("Resume Clinic")).isTrue();
	}

	@Test
	void editKeepsTheEventIdentifierAndDuplicateNamesAreRejected() throws Exception {
		Long id = eventRepository.findAll().getFirst().getId();
		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/events/{id}", id)
						.param("name", "Updated Hackathon")
						.param("city", "Delhi")
						.param("seats", "90"))
				.andExpect(status().is3xxRedirection());
		assertThat(eventRepository.findById(id).orElseThrow().getName()).isEqualTo("Updated Hackathon");
		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/events/{id}", id)
						.param("name", "Robotics Workshop")
						.param("city", "Pune")
						.param("seats", "90"))
				.andExpect(status().isOk())
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content()
						.string(org.hamcrest.Matchers.containsString("An event with this name already exists.")));
	}

	@Test
	void unknownEventPageReturnsNotFound() throws Exception {
		mockMvc.perform(get("/events/{id}", Long.MAX_VALUE))
				.andExpect(status().isNotFound());
	}
}
