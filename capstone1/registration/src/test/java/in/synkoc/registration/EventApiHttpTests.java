package in.synkoc.registration;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import in.synkoc.registration.model.Event;
import in.synkoc.registration.model.Registration;
import in.synkoc.registration.repository.EventRepository;
import in.synkoc.registration.repository.RegistrationRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventApiHttpTests {

	@LocalServerPort
	private int port;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private RegistrationRepository registrationRepository;

	private final HttpClient client = HttpClient.newHttpClient();
	private Event event;

	@BeforeEach
	void resetDatabase() {
		registrationRepository.deleteAll();
		eventRepository.deleteAll();
		Event event = eventRepository.save(new Event("Resume Clinic", "Innovation Hub",
				LocalDate.now().plusDays(7), 2));
		this.event = event;
	}

	@Test
	void realHttpEventsEndpointReturnsEventSummaryAndSeatsLeft() throws Exception {
		HttpResponse<String> response = get("/api/events");
		assertThat(response.statusCode()).isEqualTo(200);
		assertThat(response.body()).contains("\"title\":\"Resume Clinic\"", "\"capacity\":2", "\"seatsLeft\":2");
	}

	@Test
	void realHttpEventDetailEndpointDoesNotExposeStudentInformation() throws Exception {
		registrationRepository.save(new Registration(event, "Student Name", "private@example.com",
				"Example College", 2));

		HttpResponse<String> response = get("/api/events/" + event.getId());
		assertThat(response.statusCode()).isEqualTo(200);
		assertThat(response.body()).contains("\"title\":\"Resume Clinic\"", "\"seatsLeft\":1");
		assertThat(response.body()).doesNotContain("Student Name", "private@example.com",
				"Example College", "\"yearOfStudy\"", "\"email\"");
	}

	@Test
	void realHttpRegistrationListReturnsRequiredStudentFieldsWithoutEmail() throws Exception {
		registrationRepository.save(new Registration(event, "Demo Student", "private@example.com",
				"Fictional College", 2));

		HttpResponse<String> response = get("/api/events/" + event.getId() + "/registrations");
		assertThat(response.statusCode()).isEqualTo(200);
		assertThat(response.headers().firstValue("Content-Type").orElse(""))
				.contains("application/json");
		assertThat(response.body()).matches(
				"\\[\\{\\s*\"id\"\\s*:\\s*\\d+\\s*,\\s*\"name\"\\s*:\\s*\"Demo Student\"\\s*,\\s*"
						+ "\"college\"\\s*:\\s*\"Fictional College\"\\s*,\\s*\"yearOfStudy\"\\s*:\\s*2\\s*}\\s*]");
		assertThat(response.body()).doesNotContain("private@example.com", "\"email\"");
	}

	@Test
	void realHttpRegistrationListReturnsEmptyArrayWhenEventHasNoRegistrations() throws Exception {
		HttpResponse<String> response = get("/api/events/" + event.getId() + "/registrations");

		assertThat(response.statusCode()).isEqualTo(200);
		assertThat(response.headers().firstValue("Content-Type").orElse(""))
				.contains("application/json");
		assertThat(response.body()).isEqualTo("[]");
	}

	@Test
	void realHttpUnknownEventReturnsProblemDetail() throws Exception {
		HttpResponse<String> response = get("/api/events/" + Long.MAX_VALUE + "/registrations");
		assertThat(response.statusCode()).isEqualTo(404);
		assertThat(response.headers().firstValue("Content-Type").orElse(""))
				.contains("application/problem+json");
		assertThat(response.body()).contains("\"status\":404", "\"title\":\"Event not found\"");
	}

	@Test
	void realHttpRegistrationListRejectsMalformedEventId() throws Exception {
		HttpResponse<String> response = get("/api/events/not-a-number/registrations");
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(response.body()).doesNotContain("private@example.com", "Student Name");
	}

	private HttpResponse<String> get(String path) throws Exception {
		HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET().build();
		return client.send(request, HttpResponse.BodyHandlers.ofString());
	}
}
