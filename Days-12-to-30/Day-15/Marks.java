import java.util.Arrays;
import java.util.Scanner;

public class Marks {
	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			if (!scanner.hasNextLine()) {
				System.out.println("Enter at least one mark.");
				return;
			}
			String line = scanner.nextLine().trim();
			if (line.isEmpty()) {
				System.out.println("Enter at least one mark.");
				return;
			}

			int[] marks = Arrays.stream(line.split("\\s+")).mapToInt(Integer::parseInt).toArray();
			for (int mark : marks) {
				System.out.println(mark + ": " + (mark >= 50 ? "Pass" : "Fail"));
			}
			int total = Arrays.stream(marks).sum();
			double average = (double) total / marks.length;
			System.out.println("Total: " + total);
			System.out.printf("Average: %.1f%n", average);
			System.out.println("Highest: " + Arrays.stream(marks).max().orElseThrow());
			System.out.println("Lowest: " + Arrays.stream(marks).min().orElseThrow());
		}
	}
}
