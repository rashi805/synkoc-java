public class Discount {
	private Discount() {
	}

	public static double feeAfterDiscount(double fee) {
		return feeAfterDiscount(fee, 10);
	}

	public static double feeAfterDiscount(double fee, double percentage) {
		if (percentage < 0 || percentage > 100) {
			throw new IllegalArgumentException("Discount must be between 0 and 100 percent.");
		}
		return fee * (1 - percentage / 100);
	}
}
