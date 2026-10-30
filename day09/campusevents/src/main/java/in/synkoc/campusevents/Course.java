package in.synkoc.campusevents;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal fee;

	@Column(nullable = false)
	private int weeks;

	@Column(nullable = false)
	private String level;

	protected Course() {
	}

	public Course(String title, BigDecimal fee, int weeks, String level) {
		this.title = title;
		this.fee = fee;
		this.weeks = weeks;
		this.level = level;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public int getWeeks() {
		return weeks;
	}

	public String getLevel() {
		return level;
	}
}
