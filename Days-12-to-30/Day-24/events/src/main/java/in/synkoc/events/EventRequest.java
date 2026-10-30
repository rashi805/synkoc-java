package in.synkoc.events;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EventRequest(
		@NotBlank(message = "Name is required.")
		@Size(max = 60, message = "Name must be 60 characters or fewer.")
		String name,
		@NotBlank(message = "City is required.")
		String city,
		@NotNull(message = "Seats are required.")
		@Min(value = 1, message = "Seats must be at least 1.")
		@Max(value = 5000, message = "Seats cannot exceed 5000.")
		Integer seats) {
}
