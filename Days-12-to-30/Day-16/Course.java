public class Course {
	private final String name;
	private final double fee;
	private int seats;

	public Course(String name, double fee, int seats) {
		if (seats < 0) {
			throw new IllegalArgumentException("Seats cannot be negative.");
		}
		this.name = name;
		this.fee = fee;
		this.seats = seats;
	}

	public String getName() {
		return name;
	}

	public double getFee() {
		return fee;
	}

	public int getSeats() {
		return seats;
	}

	public boolean enrol() {
		if (seats == 0) {
			return false;
		}
		seats--;
		return true;
	}
}
