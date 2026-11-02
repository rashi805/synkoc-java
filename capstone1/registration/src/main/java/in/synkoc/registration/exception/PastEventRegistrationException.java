package in.synkoc.registration.exception;

public class PastEventRegistrationException extends RuntimeException {

	public PastEventRegistrationException() {
		super("Registration is closed because this event has already taken place.");
	}
}
