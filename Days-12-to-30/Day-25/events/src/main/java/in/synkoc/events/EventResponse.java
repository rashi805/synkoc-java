package in.synkoc.events;

public record EventResponse(Long id, String name, String city, int seats) {

	public static EventResponse from(Event event) {
		return new EventResponse(event.getId(), event.getName(), event.getCity(), event.getSeats());
	}
}
