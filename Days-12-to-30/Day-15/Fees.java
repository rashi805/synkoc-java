import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Fees {
	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			int count = Integer.parseInt(scanner.nextLine().trim());
			List<Long> fees = new ArrayList<>();
			for (int i = 0; i < count; i++) {
				fees.add(Long.parseLong(scanner.nextLine().trim()));
			}

			System.out.println("Fees below 10000:");
			fees.stream().filter(fee -> fee < 10_000).forEach(System.out::println);
			System.out.println("Fees descending:");
			fees.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);
			long totalWithGst = fees.stream()
					.mapToLong(fee -> Math.round(fee * 1.18))
					.sum();
			System.out.println("Total with 18% GST: " + totalWithGst);
		}
	}
}
