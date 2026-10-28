package in.synkoc.campusevents;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	private final String applicationName;

	public HomeController(@Value("${spring.application.name}") String applicationName) {
		this.applicationName = applicationName;
	}

	@GetMapping("/")
	public String home() {
		return "Welcome to " + applicationName + ", your college event registration app!";
	}
}
