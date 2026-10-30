import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Groups {
	record Student(String name, String department, int marks) {
	}

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			int count = Integer.parseInt(scanner.nextLine().trim());
			Map<String, List<Student>> groups = new TreeMap<>();
			for (int i = 0; i < count; i++) {
				String[] fields = scanner.nextLine().split(";", -1);
				if (fields.length != 3) {
					throw new IllegalArgumentException("Expected name;department;marks.");
				}
				Student student = new Student(fields[0].trim(), fields[1].trim(),
						Integer.parseInt(fields[2].trim()));
				groups.computeIfAbsent(student.department(), ignored -> new ArrayList<>()).add(student);
			}

			groups.forEach((department, students) -> {
				students.sort(Comparator.comparing(Student::name));
				double average = students.stream().mapToInt(Student::marks).average().orElse(0);
				System.out.println(department + ":");
				students.forEach(student -> System.out.println("  " + student.name()));
				System.out.printf("Average marks: %.1f%n", average);
			});
		}
	}
}
