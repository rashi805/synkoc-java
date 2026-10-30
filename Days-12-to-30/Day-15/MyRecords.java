import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class MyRecords {
	// CourseFee describes course offerings in the Day 09 internship API using a category and fee amount.
	record CourseFee(String name, String category, int number) {
	}

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			int threshold = Integer.parseInt(scanner.nextLine().trim());
			int count = Integer.parseInt(scanner.nextLine().trim());
			List<CourseFee> records = new ArrayList<>();
			Map<String, Integer> countsByCategory = new TreeMap<>();
			CourseFee biggest = null;
			for (int i = 0; i < count; i++) {
				String[] fields = scanner.nextLine().split(";", -1);
				if (fields.length != 3) {
					throw new IllegalArgumentException("Expected name;category;number.");
				}
				CourseFee record = new CourseFee(fields[0].trim(), fields[1].trim(),
						Integer.parseInt(fields[2].trim()));
				records.add(record);
				countsByCategory.put(record.category(), countsByCategory.getOrDefault(record.category(), 0) + 1);
				if (biggest == null || record.number() > biggest.number()) {
					biggest = record;
				}
			}

			countsByCategory.forEach((category, total) -> System.out.println(category + ": " + total));
			System.out.println(biggest == null ? "Biggest record: none"
					: "Biggest record: " + biggest.name() + " (" + biggest.number() + ")");
			boolean found = false;
			for (CourseFee record : records) {
				if (record.number() >= threshold) {
					System.out.println(record.name());
					found = true;
				}
			}
			if (!found) {
				System.out.println("No records meet the threshold.");
			}
		}
	}
}
