package in.synkoc.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EventNotFoundException extends RuntimeException {

	public EventNotFoundException(Long eventId) {
		super("Event " + eventId + " was not found.");
	}
}
