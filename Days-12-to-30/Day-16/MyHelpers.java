/**
 * Helpful calculations for the Day 09 campus courses application.
 */
public class MyHelpers {
	private MyHelpers() {
	}

	/** Returns whether the fee is within the requested budget. */
	public static boolean isWithinBudget(double fee, double budget) {
		return fee >= 0 && budget >= 0 && fee <= budget;
	}

	/** Returns the course fee after applying a non-negative discount percentage. */
	public static double discountedFee(double fee, double percent) {
		if (fee < 0 || percent < 0 || percent > 100) {
			throw new IllegalArgumentException("Fee and discount must be valid non-negative values.");
		}
		return fee * (1 - percent / 100);
	}

	/** Returns the available seat count after enrolment, never below zero. */
	public static int seatsAfterEnrolment(int seats, int enrolments) {
		if (seats < 0 || enrolments < 0) {
			throw new IllegalArgumentException("Seats and enrolments cannot be negative.");
		}
		return Math.max(0, seats - enrolments);
	}
}
