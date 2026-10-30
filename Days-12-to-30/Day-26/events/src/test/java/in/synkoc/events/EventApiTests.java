package in.synkoc.events;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventApiTests {

	@LocalServerPort
	private int port;

	@Autowired
	private EventRepository eventRepository;

	private final HttpClient client = HttpClient.newHttpClient();
	private Long existingId;

	@BeforeEach
	void resetDatabase() {
		eventRepository.deleteAll();
		existingId = eventRepository.save(new Event("Hackathon", "Pune", 120)).getId();
		eventRepository.save(new Event("Robotics Workshop", "Mumbai", 40));
	}

	@Test
	void validPostReturns201AndLocation() throws Exception {
		HttpResponse<String> response = send("POST", "/api/events",
				"{\"name\":\"Career Fair\",\"city\":\"Delhi\",\"seats\":80}");
		assertThat(response.statusCode()).isEqualTo(201);
		assertThat(response.headers().firstValue("Location")).isPresent();
		assertThat(response.headers().firstValue("Location").orElseThrow())
				.matches(".*/api/events/\\d+");
	}

	@Test
	void blankNameReturns400ProblemDetail() throws Exception {
		HttpResponse<String> response = send("POST", "/api/events",
				"{\"name\":\" \",\"city\":\"Pune\",\"seats\":10}");
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(response.headers().firstValue("Content-Type").orElse(""))
				.contains("application/problem+json");
		assertThat(response.body()).contains("\"errors\"", "\"name\"");
	}

	@Test
	void unknownIdReturns404() throws Exception {
		assertThat(send("GET", "/api/events/" + Long.MAX_VALUE, null).statusCode()).isEqualTo(404);
	}

	@Test
	void duplicateNameReturns409() throws Exception {
		HttpResponse<String> response = send("POST", "/api/events",
				"{\"name\":\"hAcKaThOn\",\"city\":\"Pune\",\"seats\":10}");
		assertThat(response.statusCode()).isEqualTo(409);
	}

	@Test
	void deleteReturns204AndSubsequentGetReturns404() throws Exception {
		assertThat(send("DELETE", "/api/events/" + existingId, null).statusCode()).isEqualTo(204);
		assertThat(send("GET", "/api/events/" + existingId, null).statusCode()).isEqualTo(404);
	}

	@Test
	void emptyAndWhitespaceCityFiltersReturnAllEvents() throws Exception {
		assertThat(send("GET", "/api/events?city=", null).body()).contains("Hackathon", "Robotics Workshop");
		assertThat(send("GET", "/api/events?city=%20%20", null).body()).contains("Hackathon", "Robotics Workshop");
	}

	@Test
	void searchIsCaseInsensitive() throws Exception {
		HttpResponse<String> response = send("GET", "/api/events/search?q=hAcKaThOn", null);
		assertThat(response.statusCode()).isEqualTo(200);
		assertThat(response.body()).contains("Hackathon");
	}

	@Test
	void blankSearchReturns400ProblemDetail() throws Exception {
		HttpResponse<String> response = send("GET", "/api/events/search?q=%20%20", null);
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(response.headers().firstValue("Content-Type").orElse(""))
				.contains("application/problem+json");
		assertThat(response.body()).contains("Search query must not be blank.");
	}

	private HttpResponse<String> send(String method, String path, String json) throws Exception {
		HttpRequest.Builder request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path));
		if (json == null) {
			request.method(method, HttpRequest.BodyPublishers.noBody());
		} else {
			request.header("Content-Type", "application/json")
					.method(method, HttpRequest.BodyPublishers.ofString(json));
		}
		return client.send(request.build(), HttpResponse.BodyHandlers.ofString());
	}
}
