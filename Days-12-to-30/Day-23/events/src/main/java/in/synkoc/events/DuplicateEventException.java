package in.synkoc.events;

public class DuplicateEventException extends RuntimeException {

	public DuplicateEventException(String name) {
		super("An event named \"" + name + "\" already exists.");
	}
}
