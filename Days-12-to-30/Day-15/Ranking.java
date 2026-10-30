import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Ranking {
	record Student(String name, String department, int marks) {
	}

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			int count = Integer.parseInt(scanner.nextLine().trim());
			List<Student> students = new ArrayList<>();
			for (int i = 0; i < count; i++) {
				String[] fields = scanner.nextLine().split(";", -1);
				if (fields.length != 3) {
					throw new IllegalArgumentException("Expected name;department;marks.");
				}
				students.add(new Student(fields[0].trim(), fields[1].trim(), Integer.parseInt(fields[2].trim())));
			}

			students.sort(Comparator.comparingInt(Student::marks).reversed().thenComparing(Student::name));
			for (int i = 0; i < students.size(); i++) {
				Student student = students.get(i);
				System.out.printf("%d. %s (%s) - %d%n", i + 1, student.name(), student.department(), student.marks());
			}
			if (!students.isEmpty()) {
				System.out.println("Top student: " + students.getFirst().name());
			}
		}
	}
}
