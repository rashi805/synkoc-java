package in.synkoc.registration.exception;

public class DuplicateRegistrationException extends RuntimeException {

	public DuplicateRegistrationException() {
		super("This email address is already registered for this event.");
	}
}
