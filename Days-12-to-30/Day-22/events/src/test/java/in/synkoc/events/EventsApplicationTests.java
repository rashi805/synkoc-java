package in.synkoc.events;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
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

	@Test
	void initializesFourEventsOnlyOnce() {
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
}
