import java.text.Normalizer;
import java.util.Locale;

public class TextUtils {
	private TextUtils() {
	}

	public static String initials(String fullName) {
		if (fullName == null || fullName.isBlank()) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		for (String part : fullName.trim().split("\\s+")) {
			result.appendCodePoint(part.codePointAt(0));
		}
		return result.toString().toUpperCase(Locale.ROOT);
	}

	public static String slug(String text) {
		if (text == null) {
			return "";
		}
		String normalized = Normalizer.normalize(text.trim().toLowerCase(Locale.ROOT), Normalizer.Form.NFD)
				.replaceAll("\\p{M}+", "");
		return normalized.replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
	}

	public static boolean isPalindrome(String text) {
		if (text == null) {
			return false;
		}
		String normalized = text.replaceAll("[^\\p{Alnum}]", "").toLowerCase(Locale.ROOT);
		return normalized.contentEquals(new StringBuilder(normalized).reverse());
	}

	public static int countVowels(String text) {
		if (text == null) {
			return 0;
		}
		int count = 0;
		for (char character : text.toLowerCase(Locale.ROOT).toCharArray()) {
			if ("aeiou".indexOf(character) >= 0) {
				count++;
			}
		}
		return count;
	}
}
