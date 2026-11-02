package in.synkoc.registration.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "event_id", nullable = false,
			foreignKey = @ForeignKey(name = "fk_registration_event"))
	private Event event;

	@Column(nullable = false, length = 60)
	private String name;

	@Column(nullable = false, length = 254)
	private String email;

	@Column(nullable = false, length = 100)
	private String college;

	@Column(name = "year_of_study", nullable = false)
	private Integer yearOfStudy;

	protected Registration() {
	}

	public Registration(Event event, String name, String email, String college, Integer yearOfStudy) {
		this.event = event;
		this.name = name;
		this.email = email;
		this.college = college;
		this.yearOfStudy = yearOfStudy;
	}

	public Long getId() {
		return id;
	}

	public Long getEventId() {
		return event.getId();
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
