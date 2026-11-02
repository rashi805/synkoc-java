package in.synkoc.registration.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegistrationForm {

	@NotBlank(message = "Please enter your name.")
	@Size(max = 60, message = "Name must be 60 characters or fewer.")
	private String name;

	@NotBlank(message = "Please enter your email address.")
	@Email(message = "Please enter a valid email address.")
	@Size(max = 254, message = "Email address must be 254 characters or fewer.")
	private String email;

	@NotBlank(message = "Please enter your college.")
	@Size(max = 100, message = "College must be 100 characters or fewer.")
	private String college;

	@NotNull(message = "Please choose your year of study.")
	@Min(value = 1, message = "Year of study must be between 1 and 4.")
	@Max(value = 4, message = "Year of study must be between 1 and 4.")
	private Integer yearOfStudy;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public Integer getYearOfStudy() {
		return yearOfStudy;
	}

	public void setYearOfStudy(Integer yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}
}
