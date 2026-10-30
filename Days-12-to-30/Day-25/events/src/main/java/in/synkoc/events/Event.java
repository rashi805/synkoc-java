package in.synkoc.events;

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

	@Column(nullable = false, length = 60)
	private String name;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private int seats;

	protected Event() {
	}

	public Event(String name, String city, int seats) {
		this.name = name;
		this.city = city;
		this.seats = seats;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	public int getSeats() {
		return seats;
	}

	public void replaceWith(EventRequest request) {
		this.name = request.name().trim();
		this.city = request.city().trim();
		this.seats = request.seats();
	}
}
