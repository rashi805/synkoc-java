import java.text.NumberFormat;
import java.util.Locale;

public class Money {
	private Money() {
	}

	public static double gst(double amount) {
		return amount * 0.18;
	}

	public static double round2(double value) {
		return Math.round(value * 100.0) / 100.0;
	}

	public static String formatRupees(long amount) {
		NumberFormat format = NumberFormat.getIntegerInstance(Locale.forLanguageTag("en-IN"));
		return "\u20b9" + format.format(amount);
	}
}
