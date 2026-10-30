package in.synkoc.registration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import in.synkoc.registration.data.EventDataInitializer;
import in.synkoc.registration.model.Event;
import in.synkoc.registration.model.Registration;
import in.synkoc.registration.repository.EventRepository;
import in.synkoc.registration.repository.RegistrationRepository;
import in.synkoc.registration.service.EventService;

@SpringBootTest
@AutoConfigureMockMvc
class StudentEventRegistrationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private RegistrationRepository registrationRepository;

	@Autowired
	private EventDataInitializer eventDataInitializer;

	@Autowired
	private EventService eventService;

	private Event resumeClinic;
	private Event anotherEvent;

	@BeforeEach
	void setUp() {
		registrationRepository.deleteAll();
		eventRepository.deleteAll();
		anotherEvent = eventRepository.save(new Event("Study Jam", "Library", LocalDate.now().plusDays(2), 4));
		resumeClinic = eventRepository.save(new Event("Resume Clinic", "Innovation Hub",
				LocalDate.now().plusDays(7), 2));
	}

	@Test
	void upcomingEventsAreSortedSoonestFirstAndSeatsAreCalculated() {
		eventRepository.save(new Event("Past Meetup", "Old Hall", LocalDate.now().minusDays(1), 10));

		var events = eventService.upcomingEvents();

		assertThat(events).extracting(event -> event.title()).containsExactly("Study Jam", "Resume Clinic");
		assertThat(events.get(1).seatsLeft()).isEqualTo(2);
	}

	@Test
	void initializerSeedsFourFutureEventsIncludingTwoSeatResumeClinicOnlyWhenEmpty() {
		registrationRepository.deleteAll();
		eventRepository.deleteAll();

		eventDataInitializer.run(null);

		List<Event> seededEvents = eventRepository.findAllByOrderByDateAsc();
		assertThat(seededEvents).hasSizeGreaterThanOrEqualTo(4);
		Event seededResumeClinic = seededEvents.stream()
				.filter(event -> event.getTitle().equals("Resume Clinic"))
				.findFirst()
				.orElseThrow();
		assertThat(seededResumeClinic.getCapacity()).isEqualTo(2);
		assertThat(seededEvents).allSatisfy(event -> assertThat(event.getDate()).isAfter(LocalDate.now()));

		eventDataInitializer.run(null);
		assertThat(eventRepository.count()).isEqualTo(seededEvents.size());
	}

	@Test
	void homeRedirectsAndEventsAndRegistrationPagesLoad() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().is3xxRedirection())
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl("/events"));

		mockMvc.perform(get("/events"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Resume Clinic")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("2")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Admin dashboard")));

		mockMvc.perform(get("/events/{id}/register", resumeClinic.getId()))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("yearOfStudy")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Your details")));
	}

	@Test
	void registrationValidationRejectsMissingFieldsInvalidEmailAndOutOfRangeYear() throws Exception {
		mockMvc.perform(post("/events/{id}/register", resumeClinic.getId())
						.param("name", "")
						.param("email", "not-an-email")
						.param("college", "")
						.param("yearOfStudy", "5"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Please enter your name.")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("valid email address")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Please enter your college.")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("between 1 and 4")));

		assertThat(registrationRepository.count()).isZero();
	}

	@Test
	void registrationValidationRejectsMissingEmailAndYearBelowMinimum() throws Exception {
		mockMvc.perform(post("/events/{id}/register", resumeClinic.getId())
						.param("name", "Student")
						.param("email", "")
						.param("college", "Example College")
						.param("yearOfStudy", "0"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString(
						"Please enter your email address.")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("between 1 and 4")));

		assertThat(registrationRepository.count()).isZero();
	}

	@Test
	void registrationValidationEnforcesMaximumNameLength() throws Exception {
		mockMvc.perform(post("/events/{id}/register", resumeClinic.getId())
						.param("name", "N".repeat(61))
						.param("email", "student@example.com")
						.param("college", "Example College")
						.param("yearOfStudy", "2"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString(
						"Name must be 60 characters or fewer.")));

		assertThat(registrationRepository.count()).isZero();
	}

	@Test
	void successfulRegistrationRedirectsAndSavesEmailInLowercase() throws Exception {
		mockMvc.perform(validRegistration(resumeClinic.getId(), "RASHI@Example.com", "Rashi Mishra"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("/registrations/*"));

		Registration saved = registrationRepository.findAll().getFirst();
		assertThat(saved.getEmail()).isEqualTo("rashi@example.com");
		assertThat(saved.getYearOfStudy()).isEqualTo(2);

		mockMvc.perform(get("/registrations/{id}", saved.getId()))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Rashi Mishra")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Resume Clinic")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString(saved.getId().toString())));
	}

	@Test
	void duplicateRegistrationIsCaseInsensitiveAndShowsClearError() throws Exception {
		mockMvc.perform(validRegistration(resumeClinic.getId(), "rashi@example.com", "Rashi Mishra"))
				.andExpect(status().is3xxRedirection());

		mockMvc.perform(validRegistration(resumeClinic.getId(), "RASHI@EXAMPLE.COM", "Rashi Mishra"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString(
						"This email address is already registered for this event.")));

		assertThat(registrationRepository.countByEventId(resumeClinic.getId())).isEqualTo(1);
	}

	@Test
	void sameEmailCanRegisterForDifferentEvents() throws Exception {
		mockMvc.perform(validRegistration(resumeClinic.getId(), "student@example.com", "Student"))
				.andExpect(status().is3xxRedirection());
		mockMvc.perform(validRegistration(anotherEvent.getId(), "STUDENT@example.com", "Student"))
				.andExpect(status().is3xxRedirection());

		assertThat(registrationRepository.count()).isEqualTo(2);
	}

	@Test
	void fullEventRejectsThirdRegistrationAndShowsNoSeatsLeft() throws Exception {
		mockMvc.perform(validRegistration(resumeClinic.getId(), "one@example.com", "One"))
				.andExpect(status().is3xxRedirection());
		mockMvc.perform(validRegistration(resumeClinic.getId(), "two@example.com", "Two"))
				.andExpect(status().is3xxRedirection());

		mockMvc.perform(validRegistration(resumeClinic.getId(), "three@example.com", "Three"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString(
						"This event is full. No more registrations can be accepted.")));

		assertThat(registrationRepository.countByEventId(resumeClinic.getId())).isEqualTo(2);
		assertThat(eventService.toSummary(resumeClinic).seatsLeft()).isZero();
		mockMvc.perform(get("/events"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("FULL")));
	}

	@Test
	void adminOverviewAndRegistrationDetailsShowStudentInformationOldestFirst() throws Exception {
		mockMvc.perform(validRegistration(resumeClinic.getId(), "first@example.com", "First Student"))
				.andExpect(status().is3xxRedirection());
		mockMvc.perform(validRegistration(resumeClinic.getId(), "second@example.com", "Second Student"))
				.andExpect(status().is3xxRedirection());

		mockMvc.perform(get("/admin/events"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Event overview")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("2</span> students")));

		String roster = mockMvc.perform(get("/admin/events/{id}/registrations", resumeClinic.getId()))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("first@example.com")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("First Student")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("YEAR OF STUDY")))
				.andReturn().getResponse().getContentAsString();
		assertThat(roster.indexOf("First Student")).isLessThan(roster.indexOf("Second Student"));
	}

	@Test
	void adminEmptyStateUsesRequiredMessage() throws Exception {
		mockMvc.perform(get("/admin/events/{id}/registrations", resumeClinic.getId()))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Nobody has registered yet.")));
	}

	@Test
	void unknownEventAndRegistrationPagesReturnNotFound() throws Exception {
		mockMvc.perform(get("/events/999999/register"))
				.andExpect(status().isNotFound());
		mockMvc.perform(get("/admin/events/999999/registrations"))
				.andExpect(status().isNotFound());
		mockMvc.perform(get("/registrations/999999"))
				.andExpect(status().isNotFound());
	}

	@Test
	void eventsApiReturnsUpcomingSummariesWithSeatsRemaining() throws Exception {
		mockMvc.perform(get("/api/events"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("Study Jam"))
				.andExpect(jsonPath("$[1].title").value("Resume Clinic"))
				.andExpect(jsonPath("$[1].capacity").value(2))
				.andExpect(jsonPath("$[1].seatsLeft").value(2))
				.andExpect(jsonPath("$[1].venue").value("Innovation Hub"));
	}

	@Test
	void registrationsApiReturnsPrivacySafeDtoWithoutEmail() throws Exception {
		mockMvc.perform(validRegistration(resumeClinic.getId(), "private@example.com", "Student Name"))
				.andExpect(status().is3xxRedirection());

		mockMvc.perform(get("/api/events/{id}/registrations", resumeClinic.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Student Name"))
				.andExpect(jsonPath("$[0].college").value("Example College"))
				.andExpect(jsonPath("$[0].yearOfStudy").value(2))
				.andExpect(jsonPath("$[0].email").doesNotExist())
				.andExpect(content().string(org.hamcrest.Matchers.not(
						org.hamcrest.Matchers.containsString("private@example.com"))));
	}

	@Test
	void unknownApiEventReturnsProblemDetailAsProblemJson() throws Exception {
		mockMvc.perform(get("/api/events/999999/registrations"))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType("application/problem+json"))
				.andExpect(jsonPath("$.title").value("Event not found"))
				.andExpect(jsonPath("$.status").value(404));
	}

	private org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder validRegistration(
			Long eventId, String email, String name) {
		return post("/events/{id}/register", eventId)
				.param("name", name)
				.param("email", email)
				.param("college", "Example College")
				.param("yearOfStudy", "2");
	}
}
