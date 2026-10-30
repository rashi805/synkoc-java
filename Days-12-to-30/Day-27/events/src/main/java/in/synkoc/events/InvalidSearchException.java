package in.synkoc.events;

public class InvalidSearchException extends RuntimeException {

	public InvalidSearchException() {
		super("Search query must not be blank.");
	}
}
