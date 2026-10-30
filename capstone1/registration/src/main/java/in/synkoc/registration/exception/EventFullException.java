package in.synkoc.registration.exception;

public class EventFullException extends RuntimeException {

	public EventFullException() {
		super("This event is full. No more registrations can be accepted.");
	}
}
