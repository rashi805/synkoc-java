public class MyHelpersTest {
	private static int checks;

	private static void check(boolean condition, String message) {
		checks++;
		if (!condition) {
			throw new AssertionError(message);
		}
	}

	private static void checkClose(double expected, double actual, String message) {
		check(Math.abs(expected - actual) < 0.000001, message + ": expected " + expected + ", got " + actual);
	}

	public static void main(String[] args) {
		check(MyHelpers.isWithinBudget(900, 1000), "fee within budget");
		check(!MyHelpers.isWithinBudget(1001, 1000), "fee above budget");
		checkClose(90, MyHelpers.discountedFee(100, 10), "ten percent discount");
		checkClose(75, MyHelpers.discountedFee(100, 25), "custom discount");
		check(MyHelpers.seatsAfterEnrolment(8, 3) == 5, "remaining seats");
		check(MyHelpers.seatsAfterEnrolment(2, 5) == 0, "remaining seats never negative");
		System.out.println("All " + checks + " tests passed.");
	}
}
