package in.synkoc.registration.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "registrations", uniqueConstraints = {
		@UniqueConstraint(name = "uk_registration_event_email", columnNames = { "event_id", "email" })
})
public class Registration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "event_id", nullable = false)
	private Long eventId;

	@Column(nullable = false, length = 60)
	private String name;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String college;

	@Column(name = "year_of_study", nullable = false)
	private Integer yearOfStudy;

	protected Registration() {
	}

	public Registration(Long eventId, String name, String email, String college, Integer yearOfStudy) {
		this.eventId = eventId;
		this.name = name;
		this.email = email;
		this.college = college;
		this.yearOfStudy = yearOfStudy;
	}

	public Long getId() {
		return id;
	}

	public Long getEventId() {
		return eventId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getCollege() {
		return college;
	}

	public Integer getYearOfStudy() {
		return yearOfStudy;
	}
}
