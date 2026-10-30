package in.synkoc.registration.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "events")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String venue;

	@Column(nullable = false)
	private LocalDate date;

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
