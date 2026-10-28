package in.synkoc.hello;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	private static final ZoneId INDIA_ZONE = ZoneId.of("Asia/Kolkata");
	private static final DateTimeFormatter DATE_FORMAT =
			DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter TIME_FORMAT =
			DateTimeFormatter.ofPattern("HH:mm:ss");

	@GetMapping("/hello")
	public String hello() {
		return "Hello from SYNKOC!";
	}

	@GetMapping("/api/hello")
	public HelloResponse apiHello() {
		return new HelloResponse("Hello from SYNKOC!");
	}

	@GetMapping("/api/time")
	public TimeResponse time() {
		ZonedDateTime now = ZonedDateTime.now(INDIA_ZONE);
		return new TimeResponse(
				now.format(DATE_FORMAT),
				now.format(TIME_FORMAT),
				INDIA_ZONE.getId());
	}

	public record HelloResponse(String message) {
	}

	public record TimeResponse(String date, String time, String zone) {
	}
}
