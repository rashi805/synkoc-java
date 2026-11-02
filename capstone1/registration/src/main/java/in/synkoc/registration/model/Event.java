package in.synkoc.registration.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "events")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 120)
	@Column(nullable = false, length = 120)
	private String title;

	@NotBlank
	@Size(max = 120)
	@Column(nullable = false, length = 120)
	private String venue;

	@NotNull
	@Column(nullable = false)
	private LocalDate date;

	@Min(1)
	@Column(nullable = false)
	private int capacity;

	protected Event() {
	}

	public Event(String title, String venue, LocalDate date, int capacity) {
		this.title = title;
		this.venue = venue;
		this.date = date;
		this.capacity = capacity;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getVenue() {
		return venue;
	}

	public LocalDate getDate() {
		return date;
	}

	public int getCapacity() {
		return capacity;
	}
}
