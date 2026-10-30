package in.synkoc.events;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EventForm {

	@NotBlank(message = "Event name is required.")
	@Size(max = 60, message = "Event name must be 60 characters or fewer.")
	private String name;

	@NotBlank(message = "City is required.")
	private String city;

	@NotNull(message = "Seat count is required.")
	@Min(value = 1, message = "Seats must be at least 1.")
	@Max(value = 5000, message = "Seats cannot exceed 5000.")
	private Integer seats;

	public EventForm() {
	}

	public EventForm(String name, String city, Integer seats) {
		this.name = name;
		this.city = city;
		this.seats = seats;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public EventRequest toRequest() {
		return new EventRequest(name, city, seats);
	}
}
